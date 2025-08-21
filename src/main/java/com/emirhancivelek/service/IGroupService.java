package com.emirhancivelek.service;

import com.emirhancivelek.dto.DtoGroup;
import com.emirhancivelek.dto.DtoGroupIU;

import java.util.List;

public interface IGroupService {

    public List<DtoGroup> getAllGroup();

    public DtoGroup saveNewGroup(DtoGroupIU input);

    public DtoGroup updateGroup(Long groupId,DtoGroupIU input);

    public boolean deleteGroup(Long groupId);

}
