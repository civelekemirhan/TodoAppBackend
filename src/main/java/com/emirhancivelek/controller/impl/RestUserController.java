package com.emirhancivelek.controller.impl;

import com.emirhancivelek.controller.IRestUserController;
import com.emirhancivelek.dto.DtoUser;
import com.emirhancivelek.dto.DtoUserIU;
import com.emirhancivelek.model.RootEntity;
import com.emirhancivelek.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class RestUserController extends RestBaseController implements IRestUserController {

    @Autowired
    private IUserService userService;

    @Override
    @GetMapping("/getInfo")
    public RootEntity<DtoUser> getCurrentUserInfo() {
        return ok(userService.getCurrentUserInfo());
    }

    @Override
    @PutMapping("/updateUser")
    public RootEntity<DtoUser> updateUserInfo(@Valid @RequestBody DtoUserIU request) {
        return ok(userService.updateUserInfo(request));
    }

    @Override
    @DeleteMapping("/delete")
    public boolean deleteUser() {
        return userService.deleteUser();
    }

}
