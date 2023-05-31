package io.security.corespringsecurity.security.configs;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
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

import io.security.corespringsecurity.security.common.AjaxLoginAuthenticationEntryPoint;
import io.security.corespringsecurity.security.common.FormAuthenticationDetailsSource;
import io.security.corespringsecurity.security.filter.AjaxLoginProcessingFilter;
import io.security.corespringsecurity.security.handler.AjaxAccessDeniedHandler;
import io.security.corespringsecurity.security.handler.AjaxAuthenticationFailureHandler;
import io.security.corespringsecurity.security.handler.AjaxAuthenticationSuccessHandler;
import io.security.corespringsecurity.security.provider.AjaxAuthenticationProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(1)
public class SecurityConfigAjax {

    private final UserDetailsService userDetailsService;
    private final FormAuthenticationDetailsSource authenticationDetailsSource;
    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public AuthenticationProvider ajaxAuthenticationProvider() {
        return new AjaxAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(ajaxAuthenticationProvider());
    }

    // @Bean
    // public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
    //     return configuration.getAuthenticationManager();
    // }


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
                .requestMatchers("/api/login").permitAll()
                .requestMatchers("/api/messages").hasRole("MANAGER")
                .requestMatchers("/api/**").authenticated()
            );

        http.csrf(csrf -> csrf.disable());

        // http
        //     .apply(new AjaxLoginConfigurer<>())
        //     .failureHandlerAjax(ajaxAuthenticationFailureHandler())
        //     .successHandlerAjax(ajaxAuthenticationSuccessHandler())
        //     .setAuthenticationManager(authenticationManager())
        //     .loginProcessingUrl("/api/login");
        http.
            exceptionHandling(auth -> auth
                .authenticationEntryPoint(new AjaxLoginAuthenticationEntryPoint()) //인증예외
                .accessDeniedHandler(ajaxAccessDeniedHandler()) //인가예외
            );

        return http.build();
    }

    @Bean
    public AccessDeniedHandler ajaxAccessDeniedHandler(){
        return new AjaxAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
        AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
        ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManager());
        ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler());
        ajaxLoginProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler());
        return ajaxLoginProcessingFilter;
    }

    @Bean
    public AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }
}
