package com.emirhancivelek.controller;

import com.emirhancivelek.dto.DtoGroup;
import com.emirhancivelek.dto.DtoGroupIU;
import com.emirhancivelek.model.RootEntity;

import java.util.List;

public interface IRestGroupController {

    public RootEntity<List<DtoGroup>>  getAllGroup();

    public RootEntity<DtoGroup>  saveNewGroup(DtoGroupIU input);

    public RootEntity<DtoGroup> updateGroup(Long groupId,DtoGroupIU input);

    public boolean deleteGroup(Long groupId);
}
