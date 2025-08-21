package com.emirhancivelek.controller;

import com.emirhancivelek.dto.*;
import com.emirhancivelek.model.RootEntity;

public interface IRestAuthenticationController {

    public RootEntity<DtoUser> register(RegisterRequest request);

    public RootEntity<AuthenticateResponse> authenticate(AuthenticateRequest request);

    public RootEntity<AuthenticateResponse> refreshToken(RefreshTokenRequest request);

}
