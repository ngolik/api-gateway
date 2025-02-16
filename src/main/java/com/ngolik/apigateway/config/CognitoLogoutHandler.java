package com.ngolik.apigateway.config;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

public class CognitoLogoutHandler extends SimpleUrlLogoutSuccessHandler {

    /**
     * The domain of your user pool.
     */
    private String domain = "https://<user pool domain>";

    /**
     * An allowed callback URL.
     */
    private String logoutRedirectUrl = "<logout uri>";

    /**
     * The ID of your User Pool Client.
     */
    private String userPoolClientId = "4l131p08ai6a3ag3e094omkh6a";

    /**
     * Here, we must implement the new logout URL request. We define what URL to send our request to, and set out client_id and
     * logout_uri parameters.
     */
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return UriComponentsBuilder
            .fromUri(URI.create(domain + "/logout"))
            .queryParam("client_id", userPoolClientId)
            .queryParam("logout_uri", logoutRedirectUrl)
            .encode(StandardCharsets.UTF_8)
            .build()
            .toUriString();
    }
}
