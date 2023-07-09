package io.perfume.api.auth.adapter.in.http;

import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import io.perfume.api.auth.application.port.in.dto.ReissuedTokenResult;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(controllers = TokenHandlingController.class)
public class TokenHandlingControllerTest {
  MockMvc mockMvc;
  @MockBean
  private MakeNewTokenUseCase makeNewTokenUseCase;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext,
             RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .build();
  }

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testReissueAccessToken() throws Exception {
    String refreshTokenString = "refreshToken";
    String accessToken = "sampleAccessToken";
    String newAccessToken = "newAccessToken";

    ReissuedTokenResult reissuedTokenResult =
        new ReissuedTokenResult(newAccessToken, refreshTokenString);

    given(
        makeNewTokenUseCase.reissueAccessToken(anyString(), anyString(), any(LocalDateTime.class)))
        .willReturn(reissuedTokenResult);

    mockMvc.perform(MockMvcRequestBuilders.get("/v1/reissue")
            .header("Authorization", accessToken)
            .cookie(new Cookie("X-Refresh-Token", refreshTokenString)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.header().string("Authorization", newAccessToken))
        .andExpect(MockMvcResultMatchers.cookie()
            .value("X-Refresh-Token", reissuedTokenResult.refreshToken()))
        .andDo(
            document("reissue-token",
                requestHeaders(
                    headerWithName("Authorization").description("재발급이 필요한 만료된 액세스 토큰")
                ),
                requestCookies(
                    cookieWithName("X-Refresh-Token").description("재발급을 위해 필요한 리프레시 토큰")
                ),
                responseHeaders(
                    headerWithName("Authorization").description("재발급된 액세스 토큰")
                ),
                responseCookies(
                    cookieWithName("X-Refresh-Token").description("재발급된 리프레시 토큰")
                )));
  }
}
