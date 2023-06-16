package io.perfume.api.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.common.filter.signIn.SignInDto;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.domain.Role;
import io.perfume.api.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@WebAppConfiguration
class SignInAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserQueryRepository userQueryRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 성공 시 jwt 토큰을 생성한다.")
    void successLoginGenerateToken() throws Exception {
        // given
        String email = "test@test.com";
        String password = "test12341234";
        String encodedPassword = passwordEncoder.encode(password);
        given(userQueryRepository.findByUsername(email))
                .willReturn(Optional.of(User.builder().username(email).name("test").role(Role.USER).password(encodedPassword).build()));
        SignInDto signInDto = SignInDto.builder().username(email).password(password).build();

        // when & then
        this.mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInDto))
                        .param("username", email)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String token = result.getResponse().getHeader("Authorization");
                    assertNotNull(token);
                    assertEquals(token.substring(0, 7), "Bearer ");
                });
    }
}
