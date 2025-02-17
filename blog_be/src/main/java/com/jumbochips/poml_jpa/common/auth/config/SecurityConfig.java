package com.jumbochips.poml_jpa.common.auth.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.jumbochips.poml_jpa.common.jwt.JwtFilter;
import com.jumbochips.poml_jpa.common.jwt.JwtUtil;
import com.jumbochips.poml_jpa.common.jwt.LoginFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final AuthenticationConfiguration authenticationConfiguration;
        private final JwtUtil jwtUtil;

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http,
                        AuthenticationConfiguration authenticationConfiguration) throws Exception {

                http
                                .cors(cors -> cors.configurationSource(request -> {
                                        CorsConfiguration config = new CorsConfiguration();
                                        config.setAllowedOrigins(Arrays.asList("http://localhost:3000",
                                                        "https://jumbochips.com")); // 와일드카드 제거
                                        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                                        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                                        config.setExposedHeaders(Arrays.asList("Authorization")); // 클라이언트가
                                                                                                  // Authorization 헤더를읽을
                                                                                                  // 수 있도록 허용
                                        config.setAllowCredentials(true);
                                        return config;
                                }));

                // csrf disable
                http
                                .csrf((auth) -> auth.disable());

                // Form 로그인 방식 disable
                http
                                .formLogin((auth) -> auth.disable());

                // http basic 인증 방식 disable
                http
                                .httpBasic((auth) -> auth.disable());

                // 경로별 인가 작업
                http
                                .authorizeHttpRequests((auth) -> auth
                                                .requestMatchers("/login", "/api/v1/**", "/api/v1/join", "/uploads/**")
                                                .permitAll()
                                                .requestMatchers("/admin").hasRole("ADMIN")
                                                .anyRequest().authenticated());

                http
                                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);

                http
                                .addFilterAt(new LoginFilter(
                                                authenticationManager(authenticationConfiguration), jwtUtil),
                                                UsernamePasswordAuthenticationFilter.class);

                // 세션 설정
                http
                                .sessionManagement((session) -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                return http.build();
        }
}
