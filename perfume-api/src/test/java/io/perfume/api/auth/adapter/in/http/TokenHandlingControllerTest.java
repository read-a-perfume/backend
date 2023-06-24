package io.perfume.api.auth.adapter.in.http;

import io.perfume.api.auth.application.exception.FailedMakeNewAccessTokenException;
import io.perfume.api.auth.application.exception.NotFoundRefreshTokenException;
import io.perfume.api.auth.application.port.out.RememberMeRepository;
import io.perfume.api.auth.domain.RefreshToken;
import io.perfume.api.common.advice.GlobalExceptionHandler;
import jwt.JsonWebTokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
public class TokenHandlingControllerTest {

    MockMvc mockMvc;

    @Autowired
    private RememberMeRepository rememberMeRepository;

    @MockBean
    private JsonWebTokenGenerator tokenGenerator;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }
    @Test
    @DisplayName("새로운 액세스 토큰을 발급 성공 - memory에 존재하는 토큰")
    public void testGetNewAccessTokenSuccess() throws Exception {
        // given
        String oldAccessToken = "PerfumeAccessToken";
        RefreshToken refreshToken = RefreshToken.Login(oldAccessToken, LocalDateTime.now().plusDays(3));
        rememberMeRepository.saveRefreshToken(refreshToken);

        String stubbingCreateNewAccessToken = "stubbingTempToken";
        given(tokenGenerator
                .create(
                        ArgumentMatchers.any(),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.anyInt(),
                        ArgumentMatchers.any()
                )
        ).willReturn(stubbingCreateNewAccessToken);

        // when + then
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/v1/access-token?accessToken="+oldAccessToken)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(stubbingCreateNewAccessToken));

    }

    @Test
    @DisplayName("새로운 액세스 토큰을 발급 실패 - 유효기간이 지남")
    public void testGetNewAccessTokenFailed_fast_expired_time() throws Exception {
        // given
        String oldAccessToken = "PerfumeAccessToken";
        RefreshToken refreshToken = RefreshToken.Login(oldAccessToken, LocalDateTime.now().minusSeconds(10));
        rememberMeRepository.saveRefreshToken(refreshToken);

        // when
        Throwable cause = assertThrows(
                Exception.class,
                () -> mockMvc.perform(MockMvcRequestBuilders.get("/v1/access-token?accessToken="+oldAccessToken))
        ).getCause();

        // then
        assertTrue(cause instanceof FailedMakeNewAccessTokenException);
    }

    @Test
    @DisplayName("새로운 액세스 토큰을 발급 실패 - memory에 등록되지 않은 토큰")
    public void testGetNewAccessTokenFailed_not_found_access_token() throws Exception {
        // given
        String oldAccessToken = "PerfumeAccessToken";

        // when
        Exception resolvedException = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/v1/access-token?accessToken="+oldAccessToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResolvedException();

        // then
        assertNotNull(resolvedException);
        assertTrue(resolvedException instanceof NotFoundRefreshTokenException);
    }
}
