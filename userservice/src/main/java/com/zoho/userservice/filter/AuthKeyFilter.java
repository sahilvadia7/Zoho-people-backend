package com.zoho.userservice.filter;

import com.zoho.userservice.dto.UserLoginRequest;
import com.zoho.userservice.feign.OrgClient;
import com.zoho.userservice.model.UserLogin;
import com.zoho.userservice.repository.UserLoginRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthKeyFilter extends OncePerRequestFilter {

    private final OrgClient orgClient;
    private static final List<String> EXCLUDED_URLS = List.of(
            "/api/user/login", "/api/user/reg", "/swagger-ui", "/v3/api-docs"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (EXCLUDED_URLS.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authKey = request.getHeader("X-AUTH-KEY");

        if (authKey == null || authKey.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Auth Key is missing");
            return;
        }

        try {
            ResponseEntity<Boolean> result = orgClient.validateOrgAuthKey(authKey);
            if (!Boolean.TRUE.equals(result.getBody())) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Auth Key");
                return;
            }

        } catch (Exception e) {
            // Could be FeignException.NotFound or FeignException.Unauthorized
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Auth Key");
            return;
        }

        // Authenticated
        filterChain.doFilter(request, response);
    }
}
