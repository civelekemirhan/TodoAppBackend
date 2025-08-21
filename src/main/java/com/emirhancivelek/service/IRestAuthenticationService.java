package com.emirhancivelek.service;

import com.emirhancivelek.dto.*;
import com.emirhancivelek.model.RootEntity;

public interface IRestAuthenticationService {

    public DtoUser register(RegisterRequest request);

    public AuthenticateResponse authenticate(AuthenticateRequest request);

    public AuthenticateResponse refreshToken(RefreshTokenRequest request);

}
