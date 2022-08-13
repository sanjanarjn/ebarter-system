package com.ebarter.services.user.iam;

import com.ebarter.services.user.User;
import com.ebarter.services.user.UserRepository;
import com.ebarter.services.user.UserService;
import com.ebarter.services.util.ServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String email = null;
        String authHeader = request.getHeader(ServiceConstants.AUTH_HEADER_KEY);
        if(authHeader != null && !authHeader.isBlank()) {
            String authToken = authHeader.replace(ServiceConstants.BEARER_PREFIX, "");
            if(tokenService.validateToken(authToken))
                email = tokenService.getEmailFromToken(authToken);
        }

        if(email != null) {
            User user = userRepository.findByEmail(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
                    Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
