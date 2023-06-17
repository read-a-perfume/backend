package io.perfume.api.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.common.filter.signIn.SignInDto;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.Role;
import io.perfume.api.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebAppConfiguration
@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureMockMvc
@Transactional
class SignInAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("로그인 성공 시 jwt 토큰을 생성한다.")
    void successLoginGenerateToken() throws Exception {
        // given
        String email = "test@test.com";
        String password = "test12341234";
        userRepository.save(createUser(email, password));
        SignInDto signInDto = SignInDto.builder().username(email).password(password).build();

        // when & then
        this.mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInDto))
                )
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String token = result.getResponse().getHeader("Authorization");
                    assertNotNull(token);
                    assertEquals(token.substring(0, 7), "Bearer ");
                });
    }

    @Test
    @DisplayName("로그인 실패 시 jwt 토큰을 생성하지 않는다.")
    void failLoginGenerateToken() throws Exception {
        // given
        String email = "test@test.com";
        String password = "test12341234";
        userRepository.save(createUser(email, password));
        SignInDto signInDto = SignInDto.builder().username(email).password("invalid password!@#").build();

        // when & then
        this.mockMvc.perform(post("/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInDto))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String token = result.getResponse().getHeader("Authorization");
                    assertNull(token);
                });
    }

    private User createUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return User.builder().username("test").email(email).name("test").role(Role.USER).password(encodedPassword).role(Role.USER).build();
    }
}
