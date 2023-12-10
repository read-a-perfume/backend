package io.perfume.api.common.auth;

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
  public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
    return userQueryRepository
        .findOneByEmailOrUsername(emailOrUsername)
        .map(UserPrincipal::new)
        .orElseThrow(() -> new UsernameNotFoundException(emailOrUsername));
  }
}
