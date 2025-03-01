package com.example.SpringLibraryTest3.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private  final JwtSevice jwtSevice;
    private final CustomUserDetailService customUserDetailService;

    public JwtAuthenticationFilter(JwtSevice jwtSevice, CustomUserDetailService customUserDetailService) {
        this.jwtSevice = jwtSevice;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String jwt =request.getHeader("Authorization");
        if (jwt != null){
            String email =jwtSevice.extractEmail(jwt);
            if (email != null){
                UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
                if (jwtSevice.validateToken(jwt,userDetails.getUsername())){
                    UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(userDetails, null ,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }
        }
        filterChain.doFilter(request,response);

    }
}
