package com.kelempok7.serverapp.config;

import com.kelempok7.serverapp.service.impl.AppUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig {

    private AppUserDetailService appUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(crsf -> crsf.disable())
                .cors(cors -> cors.disable())
                .authorizeRequests(auth ->
                        auth
                                .antMatchers(HttpMethod.POST, "/auth/**")
                                .permitAll().antMatchers(HttpMethod.GET,"/auth/check-email","/auth/check-username","/invoice")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .userDetailsService(appUserDetailService)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
