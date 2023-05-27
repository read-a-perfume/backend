package io.perfume.api.common.oauth2;

import io.perfume.api.user.application.port.in.CreateUserUseCase;
import io.perfume.api.user.application.port.in.dto.SignUpGeneralUserCommand;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final CreateUserUseCase createUserUseCase;
    private final DefaultOAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();

    public CustomOAuth2UserService(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        SignUpGeneralUserCommand command = new SignUpGeneralUserCommand(
                "test123",
                "test123",
                oAuth2User.getAttribute("email"),
                false,
                false,
                oAuth2User.getAttribute("name")
        );
        createUserUseCase.signUpGeneralUserByEmail(command);

        return oAuth2User;
    }
}
