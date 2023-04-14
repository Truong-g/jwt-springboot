package com.spring.jwt.config.filter;

import com.spring.jwt.jwt.JWTConfig;
import com.spring.jwt.jwt.JWTService;
import com.spring.jwt.util.BaseResponseDTO;
import com.spring.jwt.util.HelperUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
public class JWTTokenAuthenticationFilter extends OncePerRequestFilter {
    private final JWTConfig jwtConfig;
    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(jwtConfig.getHeader());
        log.info("Start do filter once per request");
        if (!ObjectUtils.isEmpty(accessToken) && accessToken.startsWith(jwtConfig.getPrefix() + " ")) {
            accessToken = accessToken.substring((jwtConfig.getPrefix() + " ").length());
            try {
                if (jwtService.isValidToken(accessToken)) {
                    Claims claims = jwtService.extractClaims(accessToken);
                    String username = claims.getSubject();
                    List<String> authorities = claims.get("authorities", List.class);
                    if (!ObjectUtils.isEmpty(username)) {
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        username,
                                        null,
                                        authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                                );

                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }

                }
            } catch (Exception e) {
                log.error("Error on filter once request, path {}, error {}: ", request.getRequestURI(), e.getMessage());
                BaseResponseDTO responseDTO = new BaseResponseDTO();
                responseDTO.setCode(String.valueOf(HttpStatus.UNAUTHORIZED));
                responseDTO.setMessage(e.getLocalizedMessage());
                String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write(json);
                return;
            }
        }
        log.info("End do filter once per request {}", request.getRequestURI());
        filterChain.doFilter(request, response);
    }
}
