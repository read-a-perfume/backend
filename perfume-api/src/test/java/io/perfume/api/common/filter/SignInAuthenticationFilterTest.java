package io.perfume.api.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.common.config.SecurityConfiguration;
import io.perfume.api.common.filter.signin.SignInDto;
import io.perfume.api.user.application.port.in.dto.SignUpGeneralUserCommand;
import io.perfume.api.user.application.service.RegisterService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@WebAppConfiguration
class SignInAuthenticationFilterTest {

    @Autowired
    RegisterService registerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        SignUpGeneralUserCommand register = new SignUpGeneralUserCommand("test@test.com", passwordEncoder.encode("test12341234"), "test@gmail.com",
                false, false, "testUser");

        registerService.signUpGeneralUserByEmail(register);
    }

    @Test
    @DisplayName("로그인 성공 시 jwt 토큰을 생성한다.")
    void successLoginGenerateToken() throws Exception {
        SignInDto signInDto = SignInDto.builder().username("test@test.com").password("test12341234").build();

        MvcResult mvcResult = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInDto))
                        .param("username", "test@test.com")
                        .param("password", "test12341234"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String token = mvcResult.getResponse().getHeader("Authorization");

        assertNotNull(token);
        assertEquals(token.substring(0, 7), "Bearer ");
    }

}
