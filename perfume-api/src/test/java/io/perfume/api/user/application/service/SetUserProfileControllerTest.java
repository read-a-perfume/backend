package io.perfume.api.user.application.service;

import groovy.util.logging.Slf4j;
import io.perfume.api.user.application.port.in.SetUserProfileUseCase;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.Role;
import io.perfume.api.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class, MockitoExtension.class})
@Transactional
@SpringBootTest
@Slf4j
class SetUserProfileControllerTest {

    private MockMvc mockMvc;

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
    @DisplayName("유저는 닉네임을 설정할 수 있다.")
    public void testSetPicture() throws Exception {
        // given
        String newName = "NewName";
        String email = "test@example.com";
        String password = "test123";
        User user = userRepository.save(createUser(email, password)).get();

        // when
        Mockito.doNothing().when(setUserProfileUseCase).setNickName(user, newName);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.put("/test-put-endpoint")
                        .param("name", newName))
                .andExpect(status().isOk());

        // then
        Mockito.verify(setUserProfileUseCase, Mockito.times(1)).setNickName(user, newName);
        securityContext.setAuthentication(null);
    }

    private User createUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return User.builder().id(1L).username("test").email(email).name("test").role(Role.USER)
                .password(encodedPassword).role(Role.USER).build();
    }
}
