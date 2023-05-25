package io.security.corespringsecurity.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.security.corespringsecurity.security.service.AccountContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    //검증을 위한 메소드
    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //authentication : 입력한 사용자의 정보가 들어있다.

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, accountContext.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());
    }

    //이 클래스의 타입과 토큰의 타입이 일치할 떄 이 클래스가 인증처리 가능
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
