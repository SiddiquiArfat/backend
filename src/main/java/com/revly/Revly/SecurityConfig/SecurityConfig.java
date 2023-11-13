package com.revly.Revly.SecurityConfig;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain taskConfig(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors ->{
                    cors.configurationSource(new CorsConfigurationSource() {
                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                            CorsConfiguration cc= new CorsConfiguration();
                            cc.setAllowedOriginPatterns(Collections.singletonList("*"));
                            cc.setAllowedMethods(Collections.singletonList("*"));
                            cc.setAllowCredentials(false);
                            cc.setExposedHeaders(Collections.singletonList("*"));
                            cc.setAllowedHeaders(Collections.singletonList("*"));
                            return cc;
                        }
                    });
                })

                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers("/swagger-ui*/**","/v3/api-docs/**").permitAll()
                            .requestMatchers("/addTutor").permitAll()
                            .requestMatchers("/addStudent").permitAll()
                    .anyRequest().authenticated();
                }).csrf(csrf -> csrf.disable())
//        .formLogin(Customizer.withDefaults())
                .addFilterBefore(new JwtTokenCheck(),BasicAuthenticationFilter.class)
                .addFilterAfter(new JwtTokenGen(), BasicAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

