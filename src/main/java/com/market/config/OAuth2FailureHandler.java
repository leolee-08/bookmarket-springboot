package com.market.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2FailureHandler
        extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {

        String errorMessage = "소셜 로그인에 실패했습니다.";

        if (exception instanceof OAuth2AuthenticationException oauthEx) {
            String errorCode = oauthEx.getError().getErrorCode();

            if ("email_already_exists".equals(errorCode)) {
                errorMessage = "이미 자체 가입된 이메일입니다. 일반 로그인을 이용해주세요.";
            } else if ("provider_mismatch".equals(errorCode)) {
                errorMessage = oauthEx.getError().getDescription();
            }
        }

        String encodedMessage = URLEncoder.encode(
                errorMessage, StandardCharsets.UTF_8);
        response.sendRedirect(
                "/member/login?oauth2Error=" + encodedMessage);
    }
}