package com.emirhancivelek.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}
