package com.ssafy.togetherhomt.user.account;

import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class AccountDto {

    @ApiParam(value = "사용자 계정 주소")
    @NotNull @Size(max = 50)
    private String email;

    @ApiParam(value = "사용자 계정 별명(nickname)")
    @NotNull @Size(min = 3, max = 10)
    private String username;

    @ApiParam(value = "비밀번호")
    @Size(min = 6)
    private String password;

}
