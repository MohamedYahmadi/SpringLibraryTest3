package com.example.SpringLibraryTest3.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter ;


    public SecurityConfig(CustomUserDetailService customUserDetailService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailService = customUserDetailService;


        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/api/student/Signup",
                            "/api/student/Login",
                            "/api/instructor/Signup",
                            "/api/instructor/Login"
                    ).permitAll();


                    auth.requestMatchers(
                            "/api/student/profile/{studentId}",
                            "/api/student/addAccountBalance",
                            "/api/student/all-courses",
                            "/api/student/course/{title}",
                            "/api/student/courses-category/{category}",
                            "/api/student/courses/price-range"


                    ).authenticated();


                    auth.requestMatchers("/api/student/buy-course/{studentId}/{courseId}")
                            .hasRole("STUDENT");
                    auth.requestMatchers(
                            "/api/student/buy-course/{studentId}/{courseId}",
                            "/api/instructor/create-course/{instructorId}",
                            "/api/instructor/update-course/{id}",
                            "/api/instructor/delete-course/{id}",
                            "/api/instructor/create-coupon/{instructorId}",
                            "/api/instructor/delete-coupon/{couponId}"
                    ).hasRole("INSTRUCTOR");

                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(customUserDetailService);
        return authProvider;
    }
}
