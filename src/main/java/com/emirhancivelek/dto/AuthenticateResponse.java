package com.emirhancivelek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateResponse {

    private String accessToken;

    private String refreshToken;
}
