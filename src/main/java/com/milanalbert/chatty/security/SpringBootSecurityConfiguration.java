package com.milanalbert.chatty.security;

import com.milanalbert.chatty.filters.JwtRequestFilter;
import com.milanalbert.chatty.services.UserDetailServiceImpl;
import com.milanalbert.chatty.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringBootSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailServiceImpl userDetailService;
  private final JwtRequestFilter jwtRequestFilter;
  private final JwtUtil jwtUtil;
  private final RestAuthenticationEntryPoint authenticationEntryPoint;

  public SpringBootSecurityConfiguration(
      UserDetailServiceImpl userDetailService,
      JwtRequestFilter jwtRequestFilter,
      JwtUtil jwtUtil,
      RestAuthenticationEntryPoint authenticationEntryPoint) {
    this.userDetailService = userDetailService;
    this.jwtRequestFilter = jwtRequestFilter;
    this.jwtUtil = jwtUtil;
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authProvider());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(
            HttpMethod.POST, "/login", "/register")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }
}
