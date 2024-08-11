package com.gear2go.config;

import com.gear2go.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "v1/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "v1/auth/authenticate").permitAll()
                        .requestMatchers(HttpMethod.POST, "v1/auth//authenticate-guest").permitAll()
                        .requestMatchers(HttpMethod.GET, "v1/product").permitAll()
                        .requestMatchers(HttpMethod.GET, "v1/product/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "v1/product/availability").permitAll()
                        .requestMatchers(HttpMethod.POST, "v1/auth/request-recovery").permitAll()
                        .requestMatchers(HttpMethod.POST, "v1/user/register").permitAll()

                        .requestMatchers(HttpMethod.GET, "v1/address").authenticated()
                        .requestMatchers(HttpMethod.POST, "v1/address").authenticated()
                        .requestMatchers(HttpMethod.PUT, "v1/address").authenticated()
                        .requestMatchers(HttpMethod.POST, "v1/auth/recover-password").authenticated()
                        .requestMatchers(HttpMethod.PUT, "v1/cart/dates").authenticated()
                        .requestMatchers(HttpMethod.PUT, "v1/cart").authenticated()
                        .requestMatchers(HttpMethod.POST, "v1/cart").authenticated()
                        .requestMatchers(HttpMethod.PUT, "v1/user/update").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "v1/user").authenticated()

                        .requestMatchers(HttpMethod.GET, "v1/address/*").hasRole(String.valueOf(Role.ADMIN))
                        .requestMatchers(HttpMethod.DELETE, "v1/address/*").hasRole(String.valueOf(Role.ADMIN))
                        .requestMatchers(HttpMethod.GET, "v1/cart/all").hasRole(String.valueOf(Role.ADMIN))
                        .requestMatchers(HttpMethod.GET, "v1/order/*").hasRole(String.valueOf(Role.ADMIN))
                        .requestMatchers(HttpMethod.DELETE, "v1/order/*").hasRole(String.valueOf(Role.ADMIN))
                        .requestMatchers(HttpMethod.POST, "v1/product/add").hasRole(String.valueOf(Role.ADMIN))
                        .requestMatchers(HttpMethod.PUT, "v1/product/update").hasRole(String.valueOf(Role.ADMIN))
                        .requestMatchers(HttpMethod.DELETE, "v1/product/*").hasRole(String.valueOf(Role.ADMIN))
                        .requestMatchers(HttpMethod.GET, "v1/user").hasRole(String.valueOf(Role.ADMIN))
                        .requestMatchers(HttpMethod.GET, "v1/user/*").hasRole(String.valueOf(Role.ADMIN))
                        .requestMatchers(HttpMethod.DELETE, "v1/user/*").hasRole(String.valueOf(Role.ADMIN))

                        .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()   //actuator endpoints
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
