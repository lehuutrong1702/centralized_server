package com.example.centralized_server.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User existed", 409),
    SignIn_Fail(1002, "Email or Password is invalid", 400),
    UN_AUTHORIZED(1001,"You are not authorized",401),
    INVALID_USERNAME(1003,"INVALID_EMAIL",400),
    INVALID_PASSWORD(1004,"INVALID_PASSWPRD",400),
    NOMINEE_NOT_EXISTED(1005,"Nominee no existed",404),
    ENTERPRISE_NOT_EXISTED(1006,"Enterprise no existed",404),
    MEMBER_NOT_EXISTED(1007, "Member not existed", 404),
    ;
    private final int code;
    private final String message;
    private final int httpStatusCode;

}
