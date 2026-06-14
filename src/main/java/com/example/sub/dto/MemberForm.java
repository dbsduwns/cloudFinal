package com.example.sub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    private String email;
    private String password;
    private String passwordConfirm;
    private String name;
    private String phone;
}
