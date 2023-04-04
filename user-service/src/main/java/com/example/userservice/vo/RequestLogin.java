package com.example.userservice.vo;

import lombok.Data;

//로그인 정보를 담아두는 클래스
@Data
public class RequestLogin {
    private String email;
    private String password;
}
