package com.gear2go.config;

import com.gear2go.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    //private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
/*        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();*/

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        .requestMatchers(   //role = ADMIN
                                "v1/*/admin/**",
                                "v1/*/admin",
                                "v1/product/crud",
                                "v1/*/admin/**"
                        ).hasRole(String.valueOf(Role.ADMIN))

                        .requestMatchers(   //permitAll
                                "v1/auth/**",
                                "v1/weather/**",
                                "v1/auth/authenticate/guest",
                                "v1/user/request-recovery",
                                "v1/product/**",
                                "v1/user/**",
                                "v1/product/availability",
                                "v1/cart/**",
                                "actuator/mappings"
                        ).permitAll()

                        .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()   //actuator endpoints
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //.authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
