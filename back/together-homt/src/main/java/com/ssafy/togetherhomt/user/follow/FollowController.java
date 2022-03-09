package com.ssafy.togetherhomt.user.follow;

import com.ssafy.togetherhomt.common.CommonService;
import com.ssafy.togetherhomt.user.User;
import com.ssafy.togetherhomt.user.UserRepository;
import com.ssafy.togetherhomt.user.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("팔로우 기능 접근 방법")
@RestController
@RequestMapping(value = "/communication/follows", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class FollowController {

    private UserRepository userRepository;

    private CommonService commonService;
    private FollowService followService;


    // 나를 팔로우 하는 사람 검색
    @ApiOperation(value = "팔로워 검색", notes = "특정 유저를 팔로우 하는 사람 검색")
    @ApiResponses({
            @ApiResponse(code = 400, message = "잘못된 요청입니다. 존재하지 않는 유저를 조회하였습니다."),
            @ApiResponse(code = 200, message = "요청한 유저의 팔로워 목록을 성공적으로 조회하였습니다.")
    })
    @GetMapping("/{email}/followers")
    public ResponseEntity<?> listFollowers(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return new ResponseEntity<>("BAD REQUEST. 존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);

        List<UserDto> followers = followService.listFollowers(user);
        return followers.size() == 0
                ? new ResponseEntity<>("아무에게도 팔로우되고 있지 않습니다.", HttpStatus.OK)
                : new ResponseEntity<>(followers, HttpStatus.OK);
    }

    // 내가 팔로우 하는 사람 검색
    @ApiOperation(value = "팔로잉 검색", notes = "특정 유저가 팔로우 하는 사람 검색")
    @ApiResponses({
            @ApiResponse(code = 400, message = "잘못된 요청입니다. 존재하지 않는 유저를 조회하였습니다."),
            @ApiResponse(code = 200, message = "요청한 유저의 팔로잉 목록을 성공적으로 조회하였습니다.")
    })
    @GetMapping("/{email}/followings")
    public ResponseEntity<?> listFollowings(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return new ResponseEntity<>("BAD REQUEST. 존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);

        List<UserDto> followings = followService.listFollowings(user);
        return followings.size() == 0
                ? new ResponseEntity<>("아무도 팔로우하고 있지 않습니다.", HttpStatus.OK)
                : new ResponseEntity<>(followings, HttpStatus.OK);
    }

    @ApiOperation(value = "팔로워/팔로잉 동시 검색", notes = "팔로워/팔로잉 목록 전체 한 번에 검색")
    @ApiResponses({
            @ApiResponse(code = 400, message = "잘못된 요청입니다. 존재하지 않는 유저를 조회하였습니다."),
            @ApiResponse(code = 200, message = "요청한 유저의 팔로워/팔로잉 목록 전체를 성공적으로 조회하였습니다.")
    })
    @GetMapping("/{email}")
    public ResponseEntity<?> listFollowersAndFollowings(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return new ResponseEntity<>("BAD REQUEST. 존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(followService.listFollowersAndFollowings(user), HttpStatus.OK);
    }

    @ApiOperation(value = "팔로우", notes = "로그인한 유저로 상대 유저 팔로우")
    @ApiResponses({
            @ApiResponse(code = 400, message = "올바르지 않은 팔로우 요청입니다."),
            @ApiResponse(code = 409, message = "올바르지 않은 팔로우 요청입니다. 해당 팔로우 관계가 이미 존재합니다."),
            @ApiResponse(code = 200, message = "요청한 팔로우를 성공적으로 등록하였습니다.")
    })
    @PostMapping("/{your-email}")
    public ResponseEntity<String> follow(@PathVariable("your-email") String yourEmail) {
        User you = userRepository.findByEmail(yourEmail);
        if (you == null)
            return new ResponseEntity<>("BAD REQUEST. 존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);
        if (you.getEmail().equals(commonService.getLoginUser().getEmail()))
            return new ResponseEntity<>("BAD REQUEST. 자신을 팔로우할 수 없습니다.", HttpStatus.BAD_REQUEST);

        Follow follow = followService.follow(you);
        if (follow == null)
            return new ResponseEntity<>("Requested follow already exists", HttpStatus.CONFLICT);

//        String msg = String.format("Follow success : %s ---> %s", me.getUsername(), you.getUsername());
        String msg = String.format("Follow success :: %s", you.getUsername());
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "팔로우 취소", notes = "로그인한 유저로 상대 유저 팔로우 취소")
    @ApiResponses({
            @ApiResponse(code = 400, message = "잘못된 요청입니다. 존재하지 않는 유저를 조회하였습니다."),
            @ApiResponse(code = 200, message = "요청한 팔로우를 성공적으로 삭제하였습니다.")
    })
    @DeleteMapping("/{your-email}")
    public ResponseEntity<String> unfollow(@PathVariable("your-email") String yourEmail) {
        User you = userRepository.findByEmail(yourEmail);
        if (you == null)
            return new ResponseEntity<>("Bad Request. 존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);

        followService.unfollow(you);
        return new ResponseEntity<>("팔로우를 취소하였습니다.", HttpStatus.OK);
    }

}