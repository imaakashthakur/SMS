package com.manjitmentor.sms.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manjitmentor.sms.builder.ResponseBuilder;
import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.dto.JwtDTO;
import com.manjitmentor.sms.repository.ApplicationUserRepository;
import com.manjitmentor.sms.security.service.JWTService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class JWTAuthFilter implements Filter {
    private final JWTService<JwtDTO> jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        log.info("doFilter from JWTAuthFilter Class is triggered!");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getRequestURI().contains("login")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        final JwtDTO jwtData = jwtService.verifyToken(token);

        log.debug("isAuth: {}", jwtData.isAuthenticated());
        log.debug("EmailAddress: {}", jwtData.getEmailAddress());

        if(!jwtData.isAuthenticated()){
            final GenericResponse genericResponse = ResponseBuilder.buildFailure("Unauthorized");
            String responseString = new ObjectMapper().writeValueAsString(genericResponse);
            response.setContentType("application/json");
            response.setStatus(401);
            response.getOutputStream().write(responseString.getBytes());
        }else {
            filterChain.doFilter(request, response);
        }
    }
}