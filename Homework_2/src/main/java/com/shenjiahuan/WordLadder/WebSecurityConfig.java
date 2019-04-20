package com.shenjiahuan.WordLadder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
                .and()
            .csrf().
                disable()
            .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        final User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder::encode);

        UserDetails user = userBuilder
                .username("SE418")
                .password("SE418")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
