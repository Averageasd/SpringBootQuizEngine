package com.example.WebQuizEngine.config;

import com.example.WebQuizEngine.domain.user.exception.JWTFilterEmptyTokenException;
import com.example.WebQuizEngine.domain.user.service.JWTService;
import com.example.WebQuizEngine.domain.user.service.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new JWTFilterEmptyTokenException("empty token");
            }
            token = authHeader.substring(7);
            token = token.replace("\"","");
            userName = jwtService.extractName(token);
            // valid token but user is not authenticated yet
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = applicationContext.getBean(UserDetailServiceImpl.class).loadUserByUsername(userName);
                if (jwtService.validateToken(token, userDetails)) {
                    // convert an instance of HttpServletRequest class into an instance of the WebAuthenticationDetails class
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    // store auth detail so next time we don't need to validate again if user has valid token
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        }

        catch (Exception e){
            exceptionResolver.resolveException(request,response, null, e);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.contains("login") || path.contains("register") || path.contains("swagger-ui") || path.contains("api-docs");
    }
}
