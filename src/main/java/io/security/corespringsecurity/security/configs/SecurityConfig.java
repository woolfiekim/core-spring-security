package io.security.corespringsecurity.security.configs;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.security.corespringsecurity.security.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    /*
    * 정적 자원이 필터 거치지 않게 함
    */

    // permitAll()과 비슷하다. 다른 점은 아래는 보안 필터 자체를 거치지 않는다.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    /*
     * 유저 인증 커스텀
     */
    // 이 빈은 써도 그만 안써도 그만이다.
    // 왜냐하면, 이미 빈으로 만들어져 있다. ProviderManager class 를 보면 된다.
    // 다만, ProviderManager를 커스텀한다면 꼭 써야한다.
    // @Bean
    // public AuthenticationManager authenticationManager() {
    //     return new ProviderManager(customAuthenticationProvider());
    // }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder());
    }


    /*
     * filter
     */
    @Bean
    public SecurityFilterChain admin(HttpSecurity http) throws Exception {

        /*
         * CustomUserDetailService > DB 에서 사용자 꺼내서 확인
         */
        // AuthenticationManagerBuilder authenticationManagerBuilder =
        //     http.getSharedObject(AuthenticationManagerBuilder.class);
        //
        // authenticationManagerBuilder.userDetailsService(userDetailsService);

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/users", "user/login/**").permitAll()
                .requestMatchers("/mypage").hasRole("USER")
                .requestMatchers("/messages").hasRole("MANAGER")
                .requestMatchers("/config").hasRole("ADMIN")
                .anyRequest().authenticated()
            );

        /*
         * 커스텀 로그인 페이지
         */
        http
            .formLogin(auth -> auth
                .loginPage("/login")
                .loginProcessingUrl("/login_proc")
                .defaultSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    /*
     * 메모리 방식으로 사용자 추가
     */
    // @Bean
    // public UserDetailsService users() {
    //
    //     String password = passwordEncoder().encode("1111");
    //
    //     UserDetails user = User.builder()
    //         .username("user")
    //         .password(password)
    //         .roles("USER")
    //         .build();
    //
    //     UserDetails manager = User.builder()
    //         .username("manager")
    //         .password(password)
    //         .roles("MANAGER", "USER")
    //         .build();
    //
    //     UserDetails admin = User.builder()
    //         .username("admin")
    //         .password(password)
    //         .roles("ADMIN", "USER", "MANAGER")
    //         .build();
    //
    //     return new InMemoryUserDetailsManager(user, manager, admin);
    // }

    /*
     * 평문인 패스워드를 암호화한다.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
