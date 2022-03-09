package com.ssafy.togetherhomt.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.togetherhomt.config.jwt.JwtAuthenticationFilter;
import com.ssafy.togetherhomt.config.jwt.JwtAuthorizationFilter;
import com.ssafy.togetherhomt.config.jwt.JwtProperties;
import com.ssafy.togetherhomt.user.Oauth2.Oauth2UserService;
import com.ssafy.togetherhomt.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;
    private final Oauth2UserService userService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .oauth2Login(
                        oauth2 -> oauth2
                                .successHandler(new AuthenticationSuccessHandler() {
                                    @Override
                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                        Object principal = authentication.getPrincipal();

                                        if(principal instanceof OAuth2User){
                                            if(principal instanceof OidcUser){
                                                // google
                                                if(userRepository.findByEmail(((OidcUser) principal).getAttribute("email"))==null){
                                                    String result = userService.googleSignup((OAuth2User) principal);
                                                    if(!result.equals("success")) return;
                                                }
                                                String jwtToken = JWT.create()
                                                        .withSubject("THT's JWT")
                                                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                                                        .withClaim("email", (String)((OidcUser) principal).getAttribute("email"))
                                                        .withClaim("username", (String) ((OidcUser) principal).getAttribute("name"))
                                                        .sign(Algorithm.HMAC512(JwtProperties.SECRET));

                                                response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
                                            }
                                            else{
                                                // naver
                                                System.out.println("principal = " + principal);
                                                Map<String, Object> resp = ((OAuth2User) principal).getAttribute("response");
                                                if(userRepository.findByEmail(""+resp.get("email"))==null){
                                                    String result = userService.naverSignup(resp);
                                                    if(!result.equals("success")) return;
                                                }
                                                String jwtToken = JWT.create()
                                                        .withSubject("THT's JWT")
                                                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                                                        .withClaim("email", ""+resp.get("email"))
                                                        .withClaim("username", ""+resp.get("name"))
                                                        .sign(Algorithm.HMAC512(JwtProperties.SECRET));

                                                response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
                                            }
                                        }
                                    }
                                })
                )
                .oauth2Login()
                    .loginPage("http://i6b206.p.ssafy.io/")
                    .permitAll()
                    .and()
                // Security-h2 충돌 방지
                .headers().frameOptions().sameOrigin()
                .and()

                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeRequests()
                .antMatchers("/user/account/sign-up/**").permitAll()
                .antMatchers("/user/account/reset-password").permitAll()
                .antMatchers("/upload/**").permitAll()
                .anyRequest().access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .and()
            .formLogin()
                .loginPage("http://i6b206.p.ssafy.io/")
                .permitAll()
                .and()
            .httpBasic().disable()
            .addFilter(corsFilter)
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository));

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/user/account/sign-up/**",
                "/user/account/reset-password",
                "/upload/**"
        );
        // ref: https://stackoverflow.com/a/37683455
    }

}
