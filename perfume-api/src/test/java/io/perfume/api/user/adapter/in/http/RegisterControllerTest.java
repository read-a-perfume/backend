package io.perfume.api.user.adapter.in.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import encryptor.TwoWayEncryptor;
import io.perfume.api.auth.application.port.out.AuthenticationKeyRepository;
import io.perfume.api.auth.domain.AuthenticationKey;
import io.perfume.api.user.adapter.in.http.dto.CheckUsernameRequestDto;
import io.perfume.api.user.adapter.in.http.dto.EmailVerifyConfirmRequestDto;
import io.perfume.api.user.adapter.in.http.dto.RegisterDto;
import io.perfume.api.user.adapter.in.http.dto.SendEmailVerifyCodeRequestDto;
import io.perfume.api.user.application.port.out.EmailCodeRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import java.time.LocalDateTime;
import mailer.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
class RegisterControllerTest {

  private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private UserRepository userRepository;

  @Autowired private AuthenticationKeyRepository authenticationKeyRepository;

  @MockBean private TwoWayEncryptor twoWayEncryptor;

  @MockBean private MailSender mailSender;

  @MockBean private EmailCodeRepository emailCodeRepository;

  @BeforeEach
  void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
  }

  @Test
  @DisplayName("본인 이메일을 인증한다.")
  void confirmEmail() throws Exception {
    // given
    LocalDateTime now = LocalDateTime.now();
    authenticationKeyRepository.save(AuthenticationKey.createAuthenticationKey("code", "key", now));
    EmailVerifyConfirmRequestDto dto = new EmailVerifyConfirmRequestDto("code", "key");
    given(twoWayEncryptor.decrypt(any())).willReturn("");

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/signup/email-verify/confirm")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "verify-email",
                requestFields(
                    fieldWithPath("code").type(JsonFieldType.STRING).description("본인 인증 코드"),
                    fieldWithPath("key")
                        .type(JsonFieldType.STRING)
                        .description("본인 인증 요청 시 발급받은 키")),
                responseFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("인증 완료된 이메일"),
                    fieldWithPath("verifiedAt")
                        .type(JsonFieldType.STRING)
                        .description("본인 인증 완료 시간"))));
  }

  @Test
  @DisplayName("본인 이메일 인증을 위한 코드 발송을 요청한다.")
  void testEmailVerifyRequest() throws Exception {
    // given
    String email = "sample@mail.com";
    SendEmailVerifyCodeRequestDto dto = new SendEmailVerifyCodeRequestDto(email);
    given(twoWayEncryptor.encrypt(any())).willReturn("");
    LocalDateTime now = LocalDateTime.now();
    given(mailSender.send(any(), any(), any())).willReturn(now);
    doNothing().when(emailCodeRepository).save(any(), any(), any());

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/signup/email-verify/request")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "send-verify-code",
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("본인 인증을 위한 이메일")),
                responseFields(
                    fieldWithPath("key")
                        .type(JsonFieldType.STRING)
                        .description("이메일 본인 인증 시 필요한 키"),
                    fieldWithPath("sentAt")
                        .type(JsonFieldType.STRING)
                        .description("본인 확인 이메일 발송 시간"))));
  }

  @Test
  @DisplayName("이메일 회원가입을 요청한다.")
  void testSignUpByEmail() throws Exception {
    // given
    RegisterDto dto = new RegisterDto("sample", "password", "test@mail.com", false, false);

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/signup/email")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "sign-up-by-email",
                requestFields(
                    fieldWithPath("username").type(JsonFieldType.STRING).description("사용자 이름"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("marketingConsent")
                        .type(JsonFieldType.BOOLEAN)
                        .description("마케팅 수신 동의 여부"),
                    fieldWithPath("promotionConsent")
                        .type(JsonFieldType.BOOLEAN)
                        .description("이용약관 동의 여부")),
                responseFields(
                    fieldWithPath("username").type(JsonFieldType.STRING).description("사용자 이름"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"))));
  }

  @Test
  @DisplayName("닉네임 중복 확인을 요청한다.")
  void testCheckUsername() throws Exception {
    // given
    CheckUsernameRequestDto dto = new CheckUsernameRequestDto("sample");
    LocalDateTime now = LocalDateTime.now();

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/signup/check-username")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andDo(
            document(
                "check-username",
                requestFields(
                    fieldWithPath("username")
                        .type(JsonFieldType.STRING)
                        .description("중복 확인을 요청할 닉네임"))));
  }

  @Test
  @DisplayName("중복 닉네임 요청 시 CONFLICT를 응답을 한다.")
  void testCheckUsernameWhenDuplicate() throws Exception {
    // given
    var now = LocalDateTime.now();
    userRepository.save(
        User.generalUserJoin("sample", "sample@test.com", "password!@#$", false, false));
    CheckUsernameRequestDto dto = new CheckUsernameRequestDto("sample");

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/signup/check-username")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isConflict())
        .andDo(document("check-username-failed"));
  }

  @Test
  @DisplayName("잘못된 이메일 형식을 요청한 경우 BAD_PARAMETER 응답을 한다.")
  void testEmailVerifyRequestIfInvalidEmailFormat() throws Exception {
    // given
    String email = "sample";
    SendEmailVerifyCodeRequestDto dto = new SendEmailVerifyCodeRequestDto(email);

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/signup/email-verify/request")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isBadRequest());
  }
}
