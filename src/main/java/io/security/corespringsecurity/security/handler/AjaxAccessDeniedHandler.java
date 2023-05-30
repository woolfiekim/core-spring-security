package io.security.corespringsecurity.security.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    /*
     * ExceptionTranslationFilter.class
     * 이 클래스는 인증과 인가 둘 다 봐준다.
     *
     * sendStartAuthentication : 인증
     * accessDeniedHandler : 인가
     *
     * 그 중에 AccessDeniedHandler 는 인가예외를 던져준다.
     */

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {

        //충분한 자격이 되지않을 경우 이 메소드를 스프링 시큐리티가 호출한다.
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access is denied");
    }
}
