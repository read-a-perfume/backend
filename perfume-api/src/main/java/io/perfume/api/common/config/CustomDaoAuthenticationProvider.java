package io.perfume.api.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Qualifier("daoAuthenticationProvider")
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    public CustomDaoAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        super(passwordEncoder);
        this.setUserDetailsService(userDetailsService);
    }
}
