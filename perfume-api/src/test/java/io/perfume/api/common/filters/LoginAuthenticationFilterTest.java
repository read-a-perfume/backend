package io.perfume.api.common.filters;

import io.perfume.api.common.configurations.SecurityConfiguration;
import io.perfume.api.user.adapter.in.http.dto.RegisterDto;
import io.perfume.api.user.application.service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@Import(SecurityConfiguration.class)
@AutoConfigureMockMvc
@Transactional
class LoginAuthenticationFilterTest {

    @Autowired
    RegisterService registerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RegisterDto register = new RegisterDto("test@test.com", passwordEncoder.encode("test12341234"), "test@gmail.com",
                false, false, "testUser");

        registerService.signUpGeneralUserByEmail(register);
    }

    @Test
    @DisplayName("로그인 성공 시 jwt 토큰을 생성한다.")
    void successLoginGenerateToken () throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.TEXT_HTML)
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