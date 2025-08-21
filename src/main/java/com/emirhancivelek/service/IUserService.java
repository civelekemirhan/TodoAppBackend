package com.emirhancivelek.service;

import com.emirhancivelek.dto.DtoUser;
import com.emirhancivelek.dto.DtoUserIU;
import com.emirhancivelek.dto.RegisterRequest;

public interface IUserService {

    public DtoUser getCurrentUserInfo();

    public DtoUser updateUserInfo(DtoUserIU request);

    public boolean deleteUser();

}
