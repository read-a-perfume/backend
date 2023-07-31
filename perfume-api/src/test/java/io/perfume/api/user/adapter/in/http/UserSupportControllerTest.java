package io.perfume.api.user.adapter.in.http;

import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import jakarta.servlet.ServletException;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
    TODO
        1. 일반 사용자(메일을 통한 가입)를 제외하고 여러 상태의 사용자를 회원탈퇴 시키기 (oauth2.0 가입사용자..)
        2. 모든 실패케이스에 대하여 httpStatus 401을 반환한다. 적절한 값으로 수정하기

 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
public class UserSupportControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @WithMockUser(username = "1", roles = "USER")
    @DisplayName("고유번호 1번 사용자(메일 가입)가 회원탈퇴를 성공했다.")
    @Test
    public void successLeaveGeneralUser() throws Exception {
        // given
        User user = getSampleGeneralUser();
        userRepository.save(user);

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isAccepted());
    }

    @WithMockUser(username = "1", roles = "USER")
    @DisplayName("고유번호 1번 사용자(메일 가입)가 회원탈퇴를 요청했으나 존재하지 않는 사용자다.")
    @Test
    public void failedLeaveMailGeneralUser_notFoundUser() throws Exception {
        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("자신의 정보를 포함하지 않고 회원탈퇴를 요청하여 실패했다.")
    @Test
    public void failedLeaveUser_withoutAuthentication() {
        // when & then
        assertThatThrownBy(() ->
                mockMvc
                        .perform(MockMvcRequestBuilders.delete("/v1/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
        ).isInstanceOf(ServletException.class);
    }

    @WithMockUser(username = "1", roles = "USER")
    @DisplayName("이미 회원탈퇴한 사용자가 다시 회원탈퇴를 요청하여 요청을 거부했다.")
    @Test
    public void failedLeaveUser_alreadyLeave() throws Exception {
        // given
        User user = getSampleGeneralUser();
        userRepository.save(user);
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/user"));

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());

    }


    private User getSampleGeneralUser() {
        return User.generalUserJoin(
                "sampleUsername",
                "sample@MailUser.com",
                "samplePassword",
                "sampleName",
                true,
                true);
    }
}
