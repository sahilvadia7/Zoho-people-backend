package com.zoho.organization.filter;

import com.zoho.organization.dto.org.OrganizationAuthKeyStore;
import com.zoho.organization.enums.STATUS;
import com.zoho.organization.model.Organization;
import com.zoho.organization.repository.OrganizationLoginRepo;
import com.zoho.organization.repository.OrganizationRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthKeyFilter extends OncePerRequestFilter{

    private final OrganizationLoginRepo orgLoginRepo;

    private static final List<String> EXCLUDED_URLS = List.of(
            "/api/org/login", "/api/org/register", "/swagger-ui", "/v3/api-docs"
    );

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //get the requestUri
        String path = request.getRequestURI();

        //check if request for the Excludes path.
        if(EXCLUDED_URLS.stream().anyMatch(path::startsWith)){
            filterChain.doFilter(request, response);
            return;
        }

        // get authKey form header
        String authKey = request.getHeader("X-AUTH-KEY");

        // check if it is null or empty
        if (authKey == null || authKey.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Auth Key is missing");
            return;
        }

        //get authKey form DB.
        OrganizationAuthKeyStore keyEntry = orgLoginRepo.findByAuthKey(authKey);

        // if Auth key not present into DB.
        if (keyEntry == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Auth Key");
            return;
        }

        UUID orgId = keyEntry.getOrganizationId();
        System.out.println("Key entry status :"+keyEntry.getStatus());
        OrganizationAuthKeyStore org = orgLoginRepo.findByOrganizationId(orgId);

        if (org == null || org.getStatus() != STATUS.ACTIVE) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Organization is not active");
            return;
        }

        // Authenticated, continue the chain
        filterChain.doFilter(request, response);


    }
}
