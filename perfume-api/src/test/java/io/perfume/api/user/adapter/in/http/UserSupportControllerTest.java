package io.perfume.api.user.adapter.in.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.user.adapter.in.http.dto.UpdateEmailRequestDto;
import io.perfume.api.user.adapter.in.http.dto.UpdatePasswordRequestDto;
import io.perfume.api.user.application.port.in.FindEncryptedUsernameUseCase;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.LeaveUserUseCase;
import io.perfume.api.user.application.port.in.SendResetPasswordMailUseCase;
import io.perfume.api.user.application.port.in.UpdateAccountUseCase;
import io.perfume.api.user.application.port.in.dto.UserProfileResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
class UserSupportControllerTest {
  private MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;
  @MockBean private FindUserUseCase findUserUseCase;
  @MockBean private LeaveUserUseCase leaveUserUseCase;
  @MockBean private UpdateAccountUseCase updateAccountUseCase;
  @MockBean private FindEncryptedUsernameUseCase findEncryptedUsernameUseCase;
  @MockBean private SendResetPasswordMailUseCase resetPasswordUserCase;

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
  @DisplayName("현재 로그인 중인 유저의 이름과 프로필 사진을 조회한다.")
  @WithMockUser(username = "1")
  void me() throws Exception {
    // given
    Long userId = 1L;
    UserProfileResult userProfileResult =
        new UserProfileResult(userId, "username", "thumbnail.com");
    given(findUserUseCase.findUserProfileById(anyLong())).willReturn(userProfileResult);

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/me").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(userProfileResult.userId()))
        .andExpect(jsonPath("$.username").value(userProfileResult.username()))
        .andExpect(jsonPath("$.thumbnail").value(userProfileResult.thumbnail()))
        .andDo(
            document(
                "get-me",
                responseFields(
                    fieldWithPath("userId").description("현재 로그인 중인 유저의 PK"),
                    fieldWithPath("username").description("현재 로그인 중인 유저의 이름"),
                    fieldWithPath("thumbnail").description("현재 로그인 중인 유저의 프로필 사진 URL"))));
  }

  @Test
  @DisplayName("현재 로그인 중이지 않다면 유저의 이름과 프로필 사진을 조회할 수 없다.")
  void failedToGetMe() throws Exception {
    // given

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/me").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.UNAUTHORIZED.value()))
        .andExpect(jsonPath("$.message").value("User Not Authenticated. Please login."))
        .andDo(
            document(
                "get-me-failed",
                responseFields(
                    fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지"))));
  }

  @Test
  @DisplayName("현재 로그인 중인 유저를 탈퇴시킨다.")
  @WithMockUser(username = "1")
  void leave() throws Exception {
    // given
    doNothing().when(leaveUserUseCase).leave(anyLong());

    // when
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.delete("/v1/user")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted())
        .andExpect(jsonPath("$.id").value("1"))
        .andDo(
            document(
                "leave-user",
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("탈퇴한 유저의 아이디"))));
  }

  @Test
  @DisplayName("유저의 이메일을 업데이트한다.")
  @WithMockUser(username = "1")
  void updateEmail() throws Exception {
    // given
    UpdateEmailRequestDto updateEmailRequestDto = new UpdateEmailRequestDto(true, "user@email.com");
    doNothing().when(updateAccountUseCase).updateUserEmail(any());

    // when
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.patch("/v1/account/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateEmailRequestDto)))
        .andExpect(status().isOk())
        .andDo(
            document(
                "update-user-email",
                requestFields(
                    fieldWithPath("verified").type(JsonFieldType.BOOLEAN).description("이메일 인증 여부"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("새로운 이메일"))));
  }

  @Test
  @DisplayName("유저의 비밀번호를 업데이트한다.")
  @WithMockUser(username = "1")
  void updatePassword() throws Exception {
    // given
    UpdatePasswordRequestDto updatePasswordRequestDto =
        new UpdatePasswordRequestDto("oldpassword", "newpassword");
    doNothing().when(updateAccountUseCase).updateUserPassword(any());

    // when
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.patch("/v1/account/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePasswordRequestDto)))
        .andExpect(status().isOk())
        .andDo(
            document(
                "update-user-password",
                requestFields(
                    fieldWithPath("oldPassword").type(JsonFieldType.STRING).description("현재 비밀번호"),
                    fieldWithPath("newPassword")
                        .type(JsonFieldType.STRING)
                        .description("새로운 비밀번호"))));
  }
}
