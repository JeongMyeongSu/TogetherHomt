package com.ssafy.togetherhomt.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api("사용자(유저) 관련 기능 접근 방법")
@RestController
@RequestMapping(value = "/user", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class UserController {

    /*** Service ***/
    private final UserService userService;
    /*** Repository ***/
    private final UserRepository userRepository;


    @ApiOperation(value = "전체 회원 조회", notes = "전체 회원 정보 조회")
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @ApiOperation(value = "유저 프로필 정보 조회", notes = "유저 프로필 정보 조회")
    @ApiResponses({
            @ApiResponse(code = 400, message = "잘못된 요청입니다. 존재하지 않는 유저입니다."),
            @ApiResponse(code = 200, message = "유저 프로필 정보 조회에 성공하였습니다.")
    })
    @GetMapping("/users/{email}")
    public ResponseEntity<?> getProfile(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return new ResponseEntity<>("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);
        else
            return ResponseEntity.ok(userService.getProfile(user));
    }

    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정")
    @ApiResponses({
            @ApiResponse(code = 404, message = "회원 정보 수정에 실패하였습니다. 잘못된 정보로 요청하였거나, 또는 리소스를 저장하던 중 오류가 발생했을 수 있습니다."),
            @ApiResponse(code = 200, message = "회원 정보 수정에 성공하였습니다.")
    })
    @PutMapping("/users/{email}")
    public ResponseEntity<?> update(@ModelAttribute UserDto userDto, @RequestPart MultipartFile picture){
        UserDto updatedUserDto = userService.update(userDto, picture);
        if (updatedUserDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @ApiOperation(value = "기본 프로필 변경", notes = "기본 프로필 변경")
    @ApiResponses({
            @ApiResponse(code = 404, message = "기본 프로필 변경에 실패하였습니다. 잘못된 정보로 요청하였거나, 또는 리소스를 저장하던 중 오류가 발생했을 수 있습니다."),
            @ApiResponse(code = 200, message = "기본 프로필 변경에 성공하였습니다.")
    })
    @PutMapping("/users/{email}/default")
    public ResponseEntity<?> setDefaultProfile(@PathVariable String email) {
        return userService.setDefaultProfile(email);
    }
}
