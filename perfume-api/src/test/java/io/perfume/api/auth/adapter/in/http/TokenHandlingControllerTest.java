package io.perfume.api.auth.adapter.in.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.cookies.CookieDocumentation.responseCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import io.perfume.api.auth.application.port.in.dto.ReissuedTokenResult;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
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
            .cookie(new Cookie("X-Access-Token", accessToken))
            .cookie(new Cookie("X-Refresh-Token", refreshTokenString)))
        .andDo(print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.cookie()
            .value("X-Access-Token", reissuedTokenResult.accessToken()))
        .andExpect(MockMvcResultMatchers.cookie()
            .value("X-Refresh-Token", reissuedTokenResult.refreshToken()))
        .andDo(
            document("reissue-access-token",
                requestCookies(
                    cookieWithName("X-Access-Token").description("만료된 Access Token"),
                    cookieWithName("X-Refresh-Token").description("재발급을 위한 Refresh Token")
                ),
                responseCookies(
                    cookieWithName("X-Access-Token").description("재발급된 Access Token"),
                    cookieWithName("X-Refresh-Token").description("재발급된 Refresh Token")
                )
            )
        );
  }
}
