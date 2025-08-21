package com.emirhancivelek.controller;

import com.emirhancivelek.dto.DtoUser;
import com.emirhancivelek.dto.DtoUserIU;
import com.emirhancivelek.model.RootEntity;

public interface IRestUserController {

    public RootEntity<DtoUser> getCurrentUserInfo();

    public RootEntity<DtoUser> updateUserInfo(DtoUserIU request);

    public boolean deleteUser();

}
