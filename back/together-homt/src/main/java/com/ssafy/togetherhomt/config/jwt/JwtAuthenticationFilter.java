package com.ssafy.togetherhomt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.togetherhomt.config.auth.PrincipalDetails;
import com.ssafy.togetherhomt.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/user/**/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!"POST".equals(request.getMethod()))
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());

        log.info("Attempting Login . . .");
        try {
//            Map<String, String[]> reqParams = request.getParameterMap();
//
//            User user = new User();
//            user.setEmail(reqParams.containsKey("email")
//                    ? reqParams.get("email")[0]
//                    : reqParams.get("username")[0]);
//            user.setPassword(reqParams.get("password")[0]);
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);

            log.info("Got login attempting user :: " + user.getEmail());

            // 로그인 시도 - 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (AuthenticationException e) {
            try {
                this.unsuccessfulAuthentication(request, response, e);
            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("THT's JWT")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("email", principalDetails.getUser().getEmail())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        log.info(String.format("----- JWT published ----- [ %s (%s) ]", principalDetails.getUser().getEmail(), principalDetails.getUser().getUsername()));
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        response.getWriter().write(JwtProperties.TOKEN_PREFIX + jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("Login Failed          " + failed.getMessage());
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
