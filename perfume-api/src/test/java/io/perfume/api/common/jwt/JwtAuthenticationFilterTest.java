package io.perfume.api.common.jwt;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import jakarta.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import jwt.JsonWebTokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@Transactional
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class JwtAuthenticationFilterTest {

  private MockMvc mockMvc;
  @Autowired private JsonWebTokenGenerator jsonWebTokenGenerator;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private AuthenticationManager authenticationManager;
  @MockBean private FindUserUseCase findUserUseCase;

  @BeforeEach
  void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    JwtAuthenticationFilter jwtAuthenticationFilter =
        new JwtAuthenticationFilter(authenticationManager, objectMapper);
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .addFilter(jwtAuthenticationFilter)
            .build();
  }

  @Test
  void FailToAuthenticateWithExpiredToken() throws Exception {
    final LocalDateTime now = LocalDateTime.now();
    final String jwt =
        jsonWebTokenGenerator.create(
            "access_token",
            Map.of("userId", 1L, "roles", List.of("ROLE_USER")),
            1,
            now.minusSeconds(1));

    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/me")
                .cookie(new Cookie("X-Access-Token", jwt)))
        .andExpect(status().isUnauthorized())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("Unauthorized"))
        .andExpect(jsonPath("$.statusCode").value("401"))
        .andExpect(jsonPath("$.message").value("User Not Authenticated. Please login."))
        .andDo(
            document(
                "token-expired",
                responseFields(
                    fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지"))));
  }
}
