package io.perfume.api.common.signIn;

import io.perfume.api.user.application.port.out.UserQueryRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  private final UserQueryRepository userQueryRepository;

  public CustomUserDetailsService(UserQueryRepository userQueryRepository) {
    this.userQueryRepository = userQueryRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userQueryRepository.findOneByEmail(email)
        .map(UserPrincipal::new)
        .orElseThrow(() -> new UsernameNotFoundException(email));
  }
}
