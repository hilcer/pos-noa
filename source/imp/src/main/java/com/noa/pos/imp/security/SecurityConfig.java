package com.noa.pos.imp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint point;
    private final JwtAuthenticationFilter filter;
    private final SecurityProperties securityProperties;
    private final String[] SECURITY_MATCHER;

    @Autowired
    SecurityConfig(JwtAuthenticationEntryPoint point, JwtAuthenticationFilter filter, SecurityProperties securityProperties, @Value("${spring.security.matcher}") String securityMatcher) {
        this.point = point;
        this.filter = filter;
        this.securityProperties = securityProperties;
        if(securityMatcher == null || securityMatcher.isEmpty() || securityMatcher.isBlank()) {
            this.SECURITY_MATCHER = new String[]{"/**"};
        } else {
            this.SECURITY_MATCHER = securityMatcher.split(";");
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(this.SECURITY_MATCHER).permitAll()
                    .anyRequest().authenticated()
                ).formLogin(form-> form.loginPage("/home/login")
                        .loginProcessingUrl("/user/signin")
                        .defaultSuccessUrl("/venta/"))
                .logout(logout -> logout.logoutUrl("/home/logout").invalidateHttpSession(true)
                        .clearAuthentication(true).permitAll());
//                .exceptionHandling(ex->ex.authenticationEntryPoint(point))
//                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                ;
        //http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(this.securityProperties.getALLOWED_ORIGINS()));
        configuration.setAllowedMethods(Arrays.asList(this.securityProperties.getALLOWED_METHODS()));
        configuration.setAllowedHeaders(Arrays.asList(this.securityProperties.getALLOWED_HEADERS()));
        configuration.setExposedHeaders(Arrays.asList(this.securityProperties.getEXPOSED_HEADERS()));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImp();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
}
