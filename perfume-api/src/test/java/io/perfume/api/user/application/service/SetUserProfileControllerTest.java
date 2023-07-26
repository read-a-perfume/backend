package io.perfume.api.user.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.common.auth.UserPrincipal;
import io.perfume.api.user.application.port.in.SetUserProfileUseCase;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.Role;
import io.perfume.api.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class, MockitoExtension.class})
@Transactional
@SpringBootTest
class SetUserProfileControllerTest {

    private MockMvc mockMvc;

    private final String email = "test@example.com";
    private final String password = "test123";

    @MockBean
    private SetUserProfileUseCase setUserProfileUseCase;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserQueryRepository userQueryRepository;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @Transactional
    @DisplayName("유저는 닉네임을 설정할 수 있다.")
    @WithMockUser
    public void testSetPicture() throws Exception {
        // given
        String name = "NewName";
        User user = userRepository.save(createUser(email, password)).get();
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // when
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        Mockito.doNothing().when(setUserProfileUseCase).setNickName(user, name);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/user/test-put-endpoint")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .param("name", name))
//                        .content("{\"name\":\"" + name + "\"}"))
                .andExpect(status().isOk());

        // then
        Mockito.verify(setUserProfileUseCase, Mockito.times(1)).setNickName(user, name);

        securityContext.setAuthentication(null);
    }

    private User createUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return User.builder().id(1L).username("test").email(email).name("test").role(Role.USER)
                .password(encodedPassword).role(Role.USER).build();
    }
}
