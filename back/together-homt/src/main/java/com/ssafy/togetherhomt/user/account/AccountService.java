package com.ssafy.togetherhomt.user.account;

import com.ssafy.togetherhomt.common.CommonService;
import com.ssafy.togetherhomt.config.media.GlobalConfig;
import com.ssafy.togetherhomt.exercise.attendance.Attendance;
import com.ssafy.togetherhomt.exercise.attendance.AttendanceRepository;
import com.ssafy.togetherhomt.user.User;
import com.ssafy.togetherhomt.user.UserRepository;
import com.ssafy.togetherhomt.util.Mailing.MailingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GlobalConfig globalConfig;

    /*** Service ***/
    private final MailingService mailingService;
    private final CommonService commonService;
    /*** Repository ***/
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;


    @Transactional
    public String signup(AccountDto account) {
        if (userRepository.findByEmail(account.getEmail()) != null)
            return "duplicate";

        User user = User.builder()
                .email(account.getEmail())
                .password(bCryptPasswordEncoder.encode(account.getPassword()))
                .username(account.getUsername())
                .role("ROLE_USER")
                .introduce("")
                .imagePath(globalConfig.getResourcePath() + "default/exercise.png")
                .build();
        userRepository.save(user);

        Attendance attendance = Attendance.builder()
                .user(user)
                .done(false)
                .build();
        attendanceRepository.save(attendance);

        return "success";
    }

    @Transactional
    public void changePassword(String newPassword){
        User user = userRepository.findByEmail(commonService.getLoginUser().getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public String resetPassword(String email) throws Exception{
        User user = userRepository.findByEmail(email);
        String newPassword = mailingService.sendSimpleMessage(email);

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));

        userRepository.save(user);

        return "success";
    }

    @Transactional
    public void withdraw(){
        User user = userRepository.findByEmail(commonService.getLoginUser().getEmail());
        userRepository.delete(user);
    }

}
