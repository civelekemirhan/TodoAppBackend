package com.emirhancivelek.service.impl;

import com.emirhancivelek.dto.DtoGroup;
import com.emirhancivelek.dto.DtoGroupIU;
import com.emirhancivelek.enums.ErrorMessageType;
import com.emirhancivelek.exception.BaseException;
import com.emirhancivelek.exception.ErrorMessage;
import com.emirhancivelek.model.Group;
import com.emirhancivelek.model.User;
import com.emirhancivelek.repository.GroupRepository;
import com.emirhancivelek.repository.TodoRepository;
import com.emirhancivelek.repository.UserRepository;
import com.emirhancivelek.service.IGroupService;
import com.emirhancivelek.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GroupServiceImpl implements IGroupService {


    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityUtil securityUtil;




    @Override
    public List<DtoGroup> getAllGroup() {
        User user = securityUtil.getAuthenticatedUser();

        List<Group> groups = groupRepository.findByUserId(user.getId());

        if(groups.isEmpty()){
            throw new BaseException(new ErrorMessage(ErrorMessageType.GROUPS_IS_NULL,""));
        }

        List<DtoGroup> dtoGroups = new ArrayList<>();
        for(Group group : groups){
            DtoGroup dtoGroup = new DtoGroup();
            BeanUtils.copyProperties(group,dtoGroup);
            dtoGroups.add(dtoGroup);
        }

        return dtoGroups;
    }


    @Override
    public DtoGroup saveNewGroup(DtoGroupIU input) {

        User user = securityUtil.getAuthenticatedUser();

        Group group = new Group();
        DtoGroup dtoGroup = new DtoGroup();
        BeanUtils.copyProperties(input,group);
        group.setCreateTime(new Date());
        group.setUser(user);
        groupRepository.save(group);

        BeanUtils.copyProperties(group,dtoGroup);

        return dtoGroup;
    }

    @Override
    @Transactional
    public DtoGroup updateGroup(Long groupId,DtoGroupIU input) {
        User user = securityUtil.getAuthenticatedUser();

        if (groupId == null) {
            throw new BaseException(new ErrorMessage(
                    ErrorMessageType.GENERAL_EXCEPTION,
                    "Güncellenecek grup ID değeri boş olamaz."
            ));
        }

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(ErrorMessageType.GROUPS_IS_NULL, "Group not found")
                ));

        if (!group.getUser().getId().equals(user.getId())) {
            throw new BaseException(new ErrorMessage(
                    ErrorMessageType.GENERAL_EXCEPTION,
                    "Bu grubu güncelleme yetkiniz yok."
            ));
        }

        group.setGroupName(input.getGroupName());
        groupRepository.save(group);

        DtoGroup dtoGroup = new DtoGroup();
        BeanUtils.copyProperties(group, dtoGroup);

        return dtoGroup;
    }

    @Override
    @Transactional
    public boolean deleteGroup(Long groupId) {
        User user = securityUtil.getAuthenticatedUser();

        if (groupId == null) {
            throw new BaseException(new ErrorMessage(
                    ErrorMessageType.GENERAL_EXCEPTION,
                    "Güncellenecek grup ID değeri boş olamaz."
            ));
        }

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(ErrorMessageType.GROUPS_IS_NULL, "Group not found")
                ));

        if (!group.getUser().getId().equals(user.getId())) {
            throw new BaseException(new ErrorMessage(
                    ErrorMessageType.GENERAL_EXCEPTION,
                    "Bu grubu güncelleme yetkiniz yok."
            ));
        }
        groupRepository.delete(group);

        return true;
    }
}
