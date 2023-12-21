package io.perfume.api.common.signout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.cookies.CookieDocumentation.responseCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.perfume.api.common.auth.Constants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessEventPublishingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@Transactional
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
class SignoutTest {

  private MockMvc mockMvc;

  @BeforeEach
  void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {

    LogoutFilter logoutFilter =
        new LogoutFilter(
            (request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK),
            (request, response, authentication) ->
                Arrays.stream(request.getCookies())
                    .map(
                        cookie -> {
                          cookie.setMaxAge(0);
                          cookie.setValue(null);
                          return cookie;
                        })
                    .forEach(response::addCookie),
            new SecurityContextLogoutHandler(),
            new LogoutSuccessEventPublishingLogoutHandler());
    logoutFilter.setFilterProcessesUrl("/v1/logout");
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .addFilter(logoutFilter)
            .build();
  }

  @Test
  void signout() throws Exception {
    String accessToken = "sampleAccessToken";
    String refreshTokenString = "refreshToken";

    mockMvc
        .perform(
            RestDocumentationRequestBuilders.post("/v1/logout")
                .cookie(new Cookie(Constants.ACCESS_TOKEN_KEY, accessToken))
                .cookie(new Cookie(Constants.REFRESH_TOKEN_KEY, refreshTokenString)))
        .andExpect(status().isOk())
        .andExpect(
            result -> {
              Cookie accessTokenCookie = result.getResponse().getCookie("X-Access-Token");
              assertNotNull(accessTokenCookie);
              assertThat(accessTokenCookie.getMaxAge()).isZero();
              assertNull(accessTokenCookie.getValue());
            })
        .andDo(
            document(
                "logout",
                requestCookies(
                    cookieWithName("X-Access-Token").description("Access Token"),
                    cookieWithName("X-Refresh-Token").description("Request Token")),
                responseCookies(
                    cookieWithName("X-Access-Token")
                        .description("Max-Age를 0으로 두어 곧 만료될 Access Token"),
                    cookieWithName("X-Refresh-Token")
                        .description("Max-Age를 0으로 두어 곧 만료될 Request Token"))));
  }
}
