package com.noa.pos.imp.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    public static final String HEADER_TOKEN_R = "TokenR";

    private final JwtHelper jwtHelper;
    @Autowired
    public JwtAuthenticationFilter(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Authorization
        String authHeader = request.getHeader("Authorization");
        LOGGER.info(String.format(" Header :  {%s}", authHeader));
        LOGGER.info(String.format(" URL :  {%s}", request.getRequestURL()));
        String username = null;
        String token = null;
        if (authHeader != null && this.jwtHelper.validateAuthHeader(authHeader)) {
            //looking good
            token = this.jwtHelper.getTokenFromAuthHeader(authHeader);
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                LOGGER.info("Illegal Argument while fetching the username !!");
            } catch (ExpiredJwtException e) {
                LOGGER.info("Given jwt token is expired !!");
            } catch (MalformedJwtException e) {
                LOGGER.info("Some changed has done in token !! Invalid Token");
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            }
        } else {
            LOGGER.info("Invalid Header Value !! ");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //fetch user detail from username
            UserDetails userDetails = User.builder().username(username).password("*****").build();
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) {
                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                response.setHeader(HEADER_TOKEN_R, jwtHelper.generateToken(userDetails));
            } else {
                LOGGER.info("Validation fails !!");
            }
        }
        filterChain.doFilter(request, response);
    }
}
