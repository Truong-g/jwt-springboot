package com.spring.jwt.config;


import com.spring.jwt.config.filter.CorsFilter;
import com.spring.jwt.config.filter.CustomAuthenticationProvider;
import com.spring.jwt.config.filter.JWTTokenAuthenticationFilter;
import com.spring.jwt.config.filter.JWTUsernamePasswordAuthenticationFilter;
import com.spring.jwt.exception.CustomAccessDeniedHandler;
import com.spring.jwt.jwt.JWTConfig;
import com.spring.jwt.jwt.JWTService;
import com.spring.jwt.service.files.StorageProperties;
import com.spring.jwt.service.security.UserDetailsServiceCustom;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppConfig {



    @Bean
    public StorageProperties storageProperties() {
        return new StorageProperties();
    }

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    JWTConfig jwtConfig;
    @Autowired
    private JWTService jwtService;


    @Bean
    public JWTConfig jwtConfig() {
        return new JWTConfig();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceCustom();
    }

    @Autowired
    public void configGlobal(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        AuthenticationManager manager = builder.build();
        http
                .csrf().disable()
                .formLogin().disable()
                .authorizeHttpRequests()
                .requestMatchers("/account/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/category/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/category/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/category").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/category/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/product/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/product/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/product").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/product/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/user/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/user/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/user/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/banner/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/banner/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/banner").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/banner/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/order/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/order/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/order").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET,"/order/**").permitAll()
                .requestMatchers("/image/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .authenticationManager(manager)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        ((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                )
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(
                        new JWTUsernamePasswordAuthenticationFilter(
                                manager,
                                jwtConfig,
                                jwtService
                        ),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenAuthenticationFilter(jwtConfig, jwtService), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
}
