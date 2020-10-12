package com.epita.filrouge.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        System.out.println("****** je suis dans JwtAuthenticationEntryPoint *******");
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        final String expired = (String) request.getAttribute("Expired");
        if (expired != null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Token expiré");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
