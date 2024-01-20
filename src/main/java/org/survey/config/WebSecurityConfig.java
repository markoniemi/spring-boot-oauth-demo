package org.survey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.survey.security.UserRepositoryAuthenticationProvider;
import jakarta.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
  @Resource
  UserRepositoryAuthenticationProvider userRepositoryAuthenticationProvider;
  @Resource
  UserDetailsService userDetailsService;
  String[] ignoredPaths = {"/", "/home", "/api/**", "/actuator/**", "/static/**", "/webjars/**"};

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http,
      UserDetailsService userDetailsService) throws Exception {
    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
    builder.authenticationProvider(userRepositoryAuthenticationProvider);
    builder.userDetailsService(userDetailsService)
        .passwordEncoder(NoOpPasswordEncoder.getInstance());
    return builder.build();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.authorizeHttpRequests(
        (auth) -> auth.requestMatchers(RegexRequestMatcher.regexMatcher(".*\\?wsdl")).permitAll());
    http.authorizeHttpRequests((auth) -> auth.requestMatchers(ignoredPaths).permitAll());
    http.formLogin((auth) -> auth.loginPage("/login").loginProcessingUrl("/j_spring_security_check")
        .defaultSuccessUrl("/user/users").usernameParameter("j_username")
        .passwordParameter("j_password").failureUrl("/login?error=true").permitAll());
    http.logout((auth) -> auth.logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/login")
        .permitAll());
    http.authorizeHttpRequests((auth) -> auth.anyRequest().authenticated());
    return http.build();
  }
}
