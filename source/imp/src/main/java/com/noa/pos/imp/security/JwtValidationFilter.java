package com.noa.pos.imp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jackson2.SimpleGrantedAuthorityMixin;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.noa.pos.imp.security.TokenJwtConfig.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //super.doFilterInternal(request, response, chain);

        var header = request.getHeader(HEADER_AUTHORIZATION);
        if (header==null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        var token = header.replace(PREFIX_TOKEN, "");

        try {
            Claims claims = Jwts.parser().verifyWith((SecretKey) SECRET_KEY).build().parseSignedClaims(token).getPayload();
            var username = claims.getSubject();
            var authoritiesClaims = claims.get("authorities");

            Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreated.class)
                    .readValue(authoritiesClaims.toString().getBytes(StandardCharsets.UTF_8), SimpleGrantedAuthority[].class));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);

        } catch (JwtException e) {
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El Token JWT es invalido!");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(CONTENT_TYPE);
        }
    }
}
