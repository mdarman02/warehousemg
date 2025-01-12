package com.neosoft.warehousemanagement.config;


import com.neosoft.warehousemanagement.jwt.JWTRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JWTRequestFilter jwtRequestFilter;

    public SecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors();
//                .disable();
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/register","/api/auth/login",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                        ).permitAll()
                .anyRequest().authenticated();
//                .anyRequest().permitAll();
        return http.build();


    }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder () {
            return new BCryptPasswordEncoder();
        }

}
