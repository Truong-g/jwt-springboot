package com.spring.jwt.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.dto.CustomResponse;
import com.spring.jwt.dto.LoginFormResponse;
import com.spring.jwt.dto.LoginFormRequest;
import com.spring.jwt.jwt.JWTConfig;
import com.spring.jwt.jwt.JWTService;
import com.spring.jwt.service.security.UserDetailsCustom;
import com.spring.jwt.util.BaseResponseDTO;
import com.spring.jwt.util.HelperUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
public class JWTUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JWTService jwtService;
    private final ObjectMapper objectMapper;

    public JWTUsernamePasswordAuthenticationFilter(AuthenticationManager manager, JWTConfig jwtConfig, JWTService jwtService) {
        super(new AntPathRequestMatcher(jwtConfig.getUrl(), "POST"));
        setAuthenticationManager(manager);
        this.objectMapper = new ObjectMapper();
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("Start attempt to authentication");

        @Valid LoginFormRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginFormRequest.class);

        log.info("End attempt to authentication");

        return getAuthenticationManager()
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword(),
                                Collections.emptyList()
                        )
                );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authResult.getPrincipal();
        String accessToken = jwtService.generateToken(userDetailsCustom);
        LoginFormResponse loginFormResponse = new LoginFormResponse();
        loginFormResponse.setUsername(userDetailsCustom.getUsername());
        loginFormResponse.setAccessToken(accessToken);
        loginFormResponse.setFullname(userDetailsCustom.getFullname());
        loginFormResponse.setRoles(userDetailsCustom.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(loginFormResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        mapper.writeValue(response.getOutputStream(), new CustomResponse<LoginFormResponse>(HttpServletResponse.SC_OK, "Success", loginFormResponse));
        log.info("End success authentication {}:", accessToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        responseDTO.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        responseDTO.setMessage(failed.getLocalizedMessage());
        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }


}
