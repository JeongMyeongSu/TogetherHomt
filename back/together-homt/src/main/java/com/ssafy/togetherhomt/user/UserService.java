package com.ssafy.togetherhomt.user;

import com.ssafy.togetherhomt.common.CommonService;
import com.ssafy.togetherhomt.config.media.GlobalConfig;
import com.ssafy.togetherhomt.config.media.MediaService;
import com.ssafy.togetherhomt.user.follow.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final GlobalConfig globalConfig;

    /*** Service ***/
    private final CommonService commonService;
    private final MediaService mediaService;
    /*** Repository ***/
    private final UserRepository userRepository;


    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserDto> userList = new ArrayList<>();
        for (User user : users) {
            userList.add(commonService.builder(user, false));
        }
        return userList;
    }

    public UserDto getProfile(User user) {
        return commonService.builder(user, true);
    }

    @Transactional
    public UserDto update(UserDto userDto, MultipartFile picture) {
        User user = userRepository.findByEmail(commonService.getLoginUser().getEmail());
        if (!user.getEmail().equals(userDto.getEmail())) {
            return null;
        }

        // Username(닉네임) 변경
        if (!user.getUsername().equals(userDto.getUsername())) {
            if (userRepository.findByUsername(userDto.getUsername()) != null)
                return null;
            else
                user.setUsername(userDto.getUsername());
        }
        // 자기소개 변경
        user.setIntroduce(userDto.getIntroduce());
        // 프로필 사진 변경
        if (picture != null) {
            String resourcePath = mediaService.save(picture);
            if (resourcePath == null)
                return null;
            else
                user.setImagePath(resourcePath);
        }

        userRepository.save(user);
        return commonService.builder(user, true);
    }

    @Transactional
    public ResponseEntity<?> setDefaultProfile(String email) {
        User user = userRepository.findByEmail(commonService.getLoginUser().getEmail());
        if (!user.getEmail().equals(email)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setImagePath(globalConfig.getResourcePath() + "/default/profile.png");
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
