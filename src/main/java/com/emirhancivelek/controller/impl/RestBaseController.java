package com.emirhancivelek.controller.impl;

import com.emirhancivelek.handler.ApiError;
import com.emirhancivelek.model.RootEntity;

public class RestBaseController {

    public <T> RootEntity<T> ok(T payload){
        return RootEntity.ok(payload);
    }

    public <T> RootEntity<T> error(ApiError<T> errorMessage){
        return RootEntity.error(errorMessage);
    }

}
