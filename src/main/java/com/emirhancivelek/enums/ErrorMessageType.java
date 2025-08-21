package com.emirhancivelek.enums;

import lombok.Getter;

@Getter
public enum ErrorMessageType {

    NO_RECORD_EXIST("1004","kayıt bulunamadı"),
    TOKEN_IS_EXPIRED("1111","token süresi bitmiştir"),
    GENERAL_EXCEPTION("9999","genel bir hata oluştu"),
    USERNAME_NOT_FOUND("2222","username bulunamadı"),
    USERNAME_OR_PASSWORD_INVALID("3333","kullanıcı adı veya şifre hatalı. "),
    REFRESH_TOKEN_NOT_FOUNT("1006","refresh token bulunamadı"),
    REFRESH_TOKEN_IS_EXPIRED("1005","refresh token süresi bitmiştir"),
    PASSWORDS_NOT_MATCH("1007","Şifreler Eşleşmiyor"),
    USER_NOT_FOUND("1008","user bulunamadı"),
    GROUPS_IS_NULL("1009","grup listesi boş"),
    TODO_NOT_FOUND("1010","todo bulunamadı"),
    USERNAME_ALREADY_EXISTS("5555","Bu kullanıcı adı zaten mevcut");

    private String code;

    private String message;

    ErrorMessageType(String code , String message){
        this.code=code;
        this.message=message;
    }


}
