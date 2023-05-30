package io.security.corespringsecurity.security.common;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AjaxLoginAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /*
     * ExceptionTranslationFilter.class
     * 이 클래스는 인증과 인가 둘 다 봐준다.
     *
     * sendStartAuthentication : 인증
     * accessDeniedHandler : 인가
     *
     * 그 중에 AuthenticationEntryPoint 는 인증예외를 던져준다.
     */

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {

        //익명 사용자가 인증이 필요한 자원에 접근했을 경우 commence메소드를 호출해서 클라이언트로 최종적으로 전달
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized");

    }
}
