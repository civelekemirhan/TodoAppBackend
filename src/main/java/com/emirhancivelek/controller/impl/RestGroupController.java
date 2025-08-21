package com.emirhancivelek.controller.impl;

import com.emirhancivelek.controller.IRestGroupController;
import com.emirhancivelek.dto.DtoGroup;
import com.emirhancivelek.dto.DtoGroupIU;
import com.emirhancivelek.model.RootEntity;
import com.emirhancivelek.service.IGroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class RestGroupController extends RestBaseController implements IRestGroupController {

    @Autowired
    private IGroupService groupService;

    @Override
    @GetMapping("/list")
    public RootEntity<List<DtoGroup>> getAllGroup() {
        return ok(groupService.getAllGroup());
    }

    @Override
    @PostMapping("/save")
    public RootEntity<DtoGroup> saveNewGroup(@Valid @RequestBody DtoGroupIU input) {
        return ok(groupService.saveNewGroup(input));
    }

    @Override
    @PutMapping("/updateGroup/{groupId}")
    public RootEntity<DtoGroup> updateGroup(@PathVariable(name = "groupId") Long groupId, @Valid @RequestBody DtoGroupIU input) {
        return ok(groupService.updateGroup(groupId,input));
    }

    @Override
    @DeleteMapping("/delete/{groupId}")
    public boolean deleteGroup(@PathVariable(name = "groupId") Long groupId) {
        return groupService.deleteGroup(groupId);
    }
}
