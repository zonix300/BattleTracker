package com.zonix.dndapp.component;

import com.zonix.dndapp.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.channels.Channel;
import java.util.List;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor {


    private static final Logger log = LoggerFactory.getLogger(AuthChannelInterceptor.class);
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthChannelInterceptor(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        StompCommand command = accessor.getCommand();

        if (StompCommand.CONNECT.equals(command)) {

            List<String> authHeaders = accessor.getNativeHeader("Authorization");

            if (authHeaders != null && !authHeaders.isEmpty()) {
                String token = authHeaders.getFirst().replace("Bearer ", "");
                String email = jwtService.extractEmail(token);

                if (email != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    if (jwtService.isTokenValid(token, userDetails.getUsername())) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        accessor.setUser(authenticationToken);
                    }
                }
            }
        }

        return message;
    }


    private void authenticateUser(StompHeaderAccessor accessor) {
        String token = extractToken(accessor);


        if (token != null) {
            try {
                Authentication authentication = validateJWTToken(token);
                if (authentication != null) {
                    accessor.setUser(authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } else {

                    throw new AccessDeniedException("Invalid JWT token");
                }
            } catch (Exception e) {

                throw new AccessDeniedException("Authentication failed: " + e.getMessage());
            }
        } else {

            throw new AccessDeniedException("No JWT token provided");
        }
    }

    private String extractToken(StompHeaderAccessor accessor) {
        List<String> authorization = accessor.getNativeHeader("Authorization");
        if (authorization != null && !authorization.isEmpty()) {
            String authHeader = authorization.get(0);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                return authHeader.substring(7);
            }
        }
        return null;
    }

    private Authentication validateJWTToken(String token) {
        try {
            String email = jwtService.extractEmail(token);

            if (email != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (jwtService.isTokenValid(token, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    log.info("JWT token validated successfully for user: {}", email);
                    return authenticationToken;
                } else {
                    log.warn("Invalid JWT token for user: {}", email);
                }
            } else {
                log.warn("Could not extract email from JWT token");
            }
        } catch (Exception e) {
            log.error("JWT token validation failed", e);
        }

        return null;
    }
}
