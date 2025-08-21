package com.emirhancivelek.controller.impl;

import com.emirhancivelek.controller.IRestAuthenticationController;
import com.emirhancivelek.dto.*;
import com.emirhancivelek.model.RootEntity;
import com.emirhancivelek.service.IRestAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController {

    @Autowired
    private IRestAuthenticationService authenticationService;


    @Override
    @PostMapping("/register")
    public RootEntity<DtoUser> register(@Valid @RequestBody RegisterRequest input){
        return ok(authenticationService.register(input));
    }


    @Override
    @PostMapping("/authenticate")
    public RootEntity<AuthenticateResponse> authenticate(@Valid @RequestBody AuthenticateRequest input) {
        return ok(authenticationService.authenticate(input));
    }

    @Override
    @PostMapping("/refreshToken")
    public RootEntity<AuthenticateResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest input) {
        return ok(authenticationService.refreshToken(input));
    }
}
