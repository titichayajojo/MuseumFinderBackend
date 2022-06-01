package com.springboot.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ResetPasswordDto {
    private String username;
    private String email;
    private String newPassword;
}
