package io.perfume.api.user.application;

import io.perfume.api.user.domain.User;
import io.perfume.api.user.fixture.TestUserRepository;
import io.perfume.api.user.infrastructure.api.dto.RegisterDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
public class RegisterServiceTest {

    private final TestUserRepository userRepository = new TestUserRepository();

    @Test
    @DisplayName("일반 사용자의 회원가입이 성공")
    public void 일반_회원가입_성공() {
        // given
        RegisterDto request = new RegisterDto(
                "perfume123", "perfume_secret", "perfume@yahoo.co.kr",
                true, true, "perfume_ms");
        RegisterService sut = new RegisterService(userRepository);

        // when
        sut.signUpGeneralUserByEmail(request);

        // then
        User user = userRepository.findByUsername(request.username()).get();

        Assertions.assertEquals(request.username(), user.getUsername());
        Assertions.assertEquals(request.email(), user.getEmail());
        Assertions.assertEquals(request.name(), user.getName());
        Assertions.assertEquals(request.promotionConsent(), user.isPromotionConsent());
        Assertions.assertEquals(request.marketingConsent(), user.isMarketingConsent());
    }
}