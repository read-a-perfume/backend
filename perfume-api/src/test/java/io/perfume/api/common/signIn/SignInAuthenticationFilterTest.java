package io.perfume.api.common.signIn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.responseCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.auth.application.port.in.MakeNewTokenUseCase;
import io.perfume.api.common.auth.CustomUserDetailsService;
import io.perfume.api.common.auth.SignInAuthenticationFilter;
import io.perfume.api.common.auth.SignInDto;
import io.perfume.api.common.auth.UserPrincipal;
import io.perfume.api.user.domain.Role;
import io.perfume.api.user.domain.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@Transactional
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class SignInAuthenticationFilterTest {

  private final String email = "test@test.com";
  private final String password = "test12341234";

  private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private PasswordEncoder passwordEncoder;
  @MockBean private CustomUserDetailsService customUserDetailsService;
  @MockBean private MakeNewTokenUseCase makeNewTokenUseCase;
  @Autowired private AuthenticationManagerBuilder authenticationManagerBuilder;

  @BeforeEach
  void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    SignInAuthenticationFilter signInAuthenticationFilter =
        new SignInAuthenticationFilter(
            authenticationManagerBuilder.getOrBuild(), objectMapper, makeNewTokenUseCase);
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .addFilter(signInAuthenticationFilter)
            .build();
  }

  @Test
  @DisplayName("로그인 성공 시 jwt 토큰을 생성한다.")
  void successLoginGenerateToken() throws Exception {
    // given
    SignInDto signInDto = SignInDto.builder().username(email).password(password).build();

    UserPrincipal userPrincipal = new UserPrincipal(createUser(email, password));
    // when & then
    when(customUserDetailsService.loadUserByUsername(anyString())).thenReturn(userPrincipal);
    when(makeNewTokenUseCase.createAccessToken(anyLong(), any(LocalDateTime.class)))
        .thenReturn("Bearer SampleAccessToken");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInDto)))
        .andExpect(status().isOk())
        .andExpect(
            result -> {
              String token = result.getResponse().getCookie("X-Access-Token").getValue();
              assertNotNull(token);
              assertEquals("Bearer ", token.substring(0, 7));
            })
        .andDo(
            document(
                "login",
                requestFields(
                    fieldWithPath("username").description("유저의 아이디"),
                    fieldWithPath("password").description("유저의 비밀번호")),
                responseCookies(
                    cookieWithName("X-Access-Token").description("액세스 토큰"),
                    cookieWithName("X-Refresh-Token").description("재발급을 위해 필요한 리프레시 토큰"))));
  }

  @Test
  @DisplayName("로그인 실패 시 jwt 토큰을 생성하지 않는다.")
  void failLoginGenerateToken() throws Exception {
    // given
    //        userRepository.save(createUser(email, password));
    SignInDto signInDto =
        SignInDto.builder().username(email).password("invalid password!@#").build();

    // when & then
    this.mockMvc
        .perform(
            post("/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInDto)))
        .andExpect(status().isUnauthorized())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            result -> {
              String token = result.getResponse().getHeader("Authorization");
              assertNull(token);
            });
  }

  private User createUser(String email, String password) {
    String encodedPassword = passwordEncoder.encode(password);
    return User.builder()
        .id(1L)
        .username("test")
        .email(email)
        .role(Role.USER)
        .password(encodedPassword)
        .role(Role.USER)
        .build();
  }
}
