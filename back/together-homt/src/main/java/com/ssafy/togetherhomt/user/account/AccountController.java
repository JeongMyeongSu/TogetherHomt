package com.ssafy.togetherhomt.user.account;

import com.ssafy.togetherhomt.common.CommonService;
import com.ssafy.togetherhomt.user.UserRepository;
import com.ssafy.togetherhomt.util.Mailing.MailingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user/account", produces = "application/json; charset=UTF-8")
@RequiredArgsConstructor
public class AccountController {

    /*** Service ***/
    private final AccountService accountService;
    private final CommonService commonService;
    private final MailingService mailingService;
    /*** Repository ***/
    private final UserRepository userRepository;


    @ApiOperation(value = "로그인 for Swagger")
    @PostMapping("/login")
    public void login(@RequestBody AccountDto accountDto) {
        return;
    }

    @ApiOperation(value = "회원 가입", notes = "회원 가입")
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 409, message = "이미 존재하는 유저입니다. 회원가입에 실패하였습니다."),
            @ApiResponse(code = 200, message = "회원가입에 성공하였습니다.")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<?> signup(@Valid @RequestBody AccountDto account) {
        String result = accountService.signup(account);

        if ("success".equals(result))
            return new ResponseEntity<>("회원가입에 성공하였습니다.", HttpStatus.OK);
        else if ("duplicate".equals(result))
            return new ResponseEntity<>("이미 가입된 계정 주소입니다.", HttpStatus.CONFLICT);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "회원 가입 승인 이메일", notes = "승인 이메일 발송")
    @ApiResponses({
            @ApiResponse(code = 500, message = "존재하지 않는 계정입니다."),
            @ApiResponse(code = 200, message = "회원가입 인증 코드가 정상적으로 발송되었습니다.")
    })
    @PostMapping("/sign-up/confirm")
    public ResponseEntity<String> confirmMail(@RequestBody String email) throws Exception {
        return ResponseEntity.ok(mailingService.sendSimpleMessage(email));
    }

    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호 찾기 후 생성된 난수로 비밀번호 초기화")
    @ApiResponses({
            @ApiResponse(code = 400, message = "잘못된 요청입니다. 계정을 명시하지 않았거나 로그인 정보와 맞지 않습니다."),
            @ApiResponse(code = 200, message = "비밀번호 재설정에 성공하였습니다.")
    })
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody String newPassword){
        accountService.changePassword(newPassword);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    @ApiOperation(value = "비밀번호 초기화", notes = "비밀번호 찾기 과정 중, 난수를 생성하여 비밀번호를 초기화하고, 메일로 전송합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "임시 재설정 비밀번호를 메일로 전송하는 데 성공하였습니다.")
    })
    @PatchMapping("/reset-password")
    public String resetPassword(@RequestBody String email) throws Exception{
        return accountService.resetPassword(email);
    }

    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴")
    @ApiResponses({
            @ApiResponse(code = 400, message = "잘못된 요청입니다. 계정을 명시하지 않았거나 로그인 정보와 맞지 않습니다."),
            @ApiResponse(code = 200, message = "회원 탈퇴에 성공하였습니다.")
    })
    @DeleteMapping("/withdrawal")
    public ResponseEntity<?> withdraw(){
        accountService.withdraw();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
