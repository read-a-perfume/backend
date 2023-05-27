package io.perfume.api.common.oauth2;

import com.mysql.cj.util.StringUtils;
import io.perfume.api.user.application.port.in.CreateUserUseCase;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.SignUpGeneralUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final CreateUserUseCase createUserUseCase;

    private final FindUserUseCase findUserUseCase;

    private final DefaultOAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        this.createNewUserIfNotExists(
                oAuth2User.getAttribute("email"),
                oAuth2User.getAttribute("name"));

        return oAuth2User;
    }

    private void createNewUserIfNotExists(String email, String name) {
        if (StringUtils.isNullOrEmpty(email)) {
            throw new IllegalArgumentException("Email must be provided");
        }

        if (StringUtils.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("Name must be provided");
        }

        if (findUserUseCase.findOneByEmail(email).isEmpty()) {
            SignUpGeneralUserCommand command = new SignUpGeneralUserCommand(
                    "test123",
                    "test123",
                    email,
                    false,
                    false,
                    name
            );
            createUserUseCase.signUpGeneralUserByEmail(command);
        }
    }
}
