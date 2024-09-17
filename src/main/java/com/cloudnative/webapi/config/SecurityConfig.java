package com.cloudnative.webapi.config;


import com.cloudnative.webapi.config.auth.BasicAuthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    private final AuthenticationProvider authenticationProvider;
    private final BasicAuthEntryPoint basicAuthEntryPoint;

    public SecurityConfig(AuthenticationProvider authenticationProvider, BasicAuthEntryPoint basicAuthEntryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.basicAuthEntryPoint = basicAuthEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new String[]{
                                "/healthz",
                                "/v1/user",
                                "/v1/user/verifyEmail",
                                "/error"
                        }).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(basicAuthEntryPoint)
                );
        return http.build();
    }
}
