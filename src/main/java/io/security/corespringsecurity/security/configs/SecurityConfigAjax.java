package io.security.corespringsecurity.security.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.security.corespringsecurity.repository.UserRepository;
import io.security.corespringsecurity.security.common.FormAuthenticationDetailsSource;
import io.security.corespringsecurity.security.filter.AjaxLoginProcessingFilter;
import io.security.corespringsecurity.security.handler.CustomAccessDeniedHandler;
import io.security.corespringsecurity.security.provider.CustomAuthenticationProvider;
import io.security.corespringsecurity.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(0)
public class SecurityConfigAjax {

    private final UserDetailsService userDetailsService;
    private final FormAuthenticationDetailsSource authenticationDetailsSource;
    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final AuthenticationFailureHandler customAuthenticationFailureHandler;

    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private void setAjaxSecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /*
     * filter
     */
    @Bean
    public SecurityFilterChain admin(HttpSecurity http) throws Exception {

        http
            .addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
        // .addFilterBefore() //기존의 필터 앞에 위치할 때
        // .addFilter() //가장 맨 마지막에 위치할 때
        // .addFilterAfter() //지금 추가하고자 하는 필터가 기존의 필터의 뒤쪽에 위치할 때
        // .addFilterAt() //기존의 필터의 위치를 대체하고자 할 때

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").authenticated()
            );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

   @Bean
   public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
       AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
       ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
       // ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler());
       // ajaxLoginProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler());
       return ajaxLoginProcessingFilter;
   }
}