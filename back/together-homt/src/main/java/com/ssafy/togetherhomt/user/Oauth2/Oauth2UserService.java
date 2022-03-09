package com.ssafy.togetherhomt.user.Oauth2;

import com.ssafy.togetherhomt.user.User;
import com.ssafy.togetherhomt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Oauth2UserService {

    UserRepository userRepository;

    @Autowired
    public Oauth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String googleSignup(OAuth2User oAuth2User){
        String email = oAuth2User.getAttribute("email");

        User user = User.builder()
                .email(email)
                .username(oAuth2User.getAttribute("name"))
                .role("ROLE_USER")
                .build();
        userRepository.save(user);

        return "success";
    }

    public String naverSignup(Map<String, Object> resp) {
        User user = User.builder()
                .email(""+resp.get("email"))
                .username(""+resp.get("name"))
                .role("ROLE_USER")
                .build();
        userRepository.save(user);
        return "success";
    }
}
