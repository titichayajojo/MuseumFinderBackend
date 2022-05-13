package com.springboot.payload;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class SignUpDto {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String username;
    private String email;
    private String password;
    private String role;
    private int zipCode;
    private ArrayList<String> tags;
}
