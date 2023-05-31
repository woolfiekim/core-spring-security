// package io.security.corespringsecurity.security.configs;
//
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
// import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
// import org.springframework.security.web.authentication.AuthenticationFailureHandler;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
// import org.springframework.security.web.authentication.RememberMeServices;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
// import org.springframework.security.web.util.matcher.RequestMatcher;
//
// import io.security.corespringsecurity.security.filter.AjaxLoginProcessingFilter;
//
// public final class AjaxLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
//     AbstractAuthenticationFilterConfigurer<H, AjaxLoginConfigurer<H>, AjaxLoginProcessingFilter> {
//
//     private AuthenticationSuccessHandler successHandler;
//     private AuthenticationFailureHandler failureHandler;
//     private AuthenticationManager authenticationManager;
//
//     public AjaxLoginConfigurer() {
//         super(new AjaxLoginProcessingFilter(), null);
//     }
//
//     @Override
//     public void init(H http) throws Exception {
//         super.init(http);
//     }
//
//     @Override
//     public void configure(H http) {
//
//         if(authenticationManager == null){
//             authenticationManager = http.getSharedObject(AuthenticationManager.class); //공유객체를 가져오고 저장
//         }
//         //SecurityConfigAjax에서 ajaxLoginProcessingFilter 부분을 여기다가 쓴 것
//         getAuthenticationFilter().setAuthenticationManager(authenticationManager);
//         getAuthenticationFilter().setAuthenticationSuccessHandler(successHandler);
//         getAuthenticationFilter().setAuthenticationFailureHandler(failureHandler);
//
//         //세션 관련된 설정
//         SessionAuthenticationStrategy sessionAuthenticationStrategy = http
//             .getSharedObject(SessionAuthenticationStrategy.class);
//         if (sessionAuthenticationStrategy != null) {
//             getAuthenticationFilter().setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
//         }
//
//         //리멤버미 설정
//         RememberMeServices rememberMeServices = http
//             .getSharedObject(RememberMeServices.class);
//         if (rememberMeServices != null) {
//             getAuthenticationFilter().setRememberMeServices(rememberMeServices);
//         }
//
//         //커스텀한 비동기 로그인 필터
//         http.setSharedObject(AjaxLoginProcessingFilter.class,getAuthenticationFilter());
//         //SecurityConfigAjax에서 addFilterBefore 부분
//         http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//     }
//
//     public AjaxLoginConfigurer<H> successHandlerAjax(AuthenticationSuccessHandler successHandler) {
//         this.successHandler = successHandler;
//         return this;
//     }
//
//     public AjaxLoginConfigurer<H> failureHandlerAjax(AuthenticationFailureHandler authenticationFailureHandler) {
//         this.failureHandler = authenticationFailureHandler;
//         return this;
//     }
//
//     public AjaxLoginConfigurer<H> setAuthenticationManager(AuthenticationManager authenticationManager) {
//         this.authenticationManager = authenticationManager;
//         return this;
//     }
//
//     @Override
//     protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
//         return new AntPathRequestMatcher(loginProcessingUrl, "POST");
//     }
//
// }
