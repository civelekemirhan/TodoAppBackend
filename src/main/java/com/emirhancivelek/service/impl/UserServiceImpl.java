package com.emirhancivelek.service.impl;

import com.emirhancivelek.dto.DtoTodo;
import com.emirhancivelek.dto.DtoUser;
import com.emirhancivelek.dto.DtoUserIU;
import com.emirhancivelek.enums.ErrorMessageType;
import com.emirhancivelek.exception.BaseException;
import com.emirhancivelek.exception.ErrorMessage;
import com.emirhancivelek.jwt.JwtService;
import com.emirhancivelek.model.User;
import com.emirhancivelek.repository.UserRepository;
import com.emirhancivelek.service.IUserService;
import com.emirhancivelek.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    @Transactional(readOnly = true)
    public DtoUser getCurrentUserInfo() {

        User user = securityUtil.getAuthenticatedUser();

        DtoUser dtoUser = new DtoUser();
        dtoUser.setId(user.getId());
        dtoUser.setUsername(user.getUsername());
        dtoUser.setFirstName(user.getFirstName());
        dtoUser.setLastName(user.getLastName());
        dtoUser.setGenderType(user.getGenderType());
        dtoUser.setCreateTime(user.getCreateTime());

        List<DtoTodo> dtoTodos = Collections.emptyList();
        if (user.getTodos() != null) {
            dtoTodos = user.getTodos()
                    .stream()
                    .map(todo -> {
                        DtoTodo dto = new DtoTodo();
                        dto.setId(todo.getId());
                        dto.setTitle(todo.getTitle());
                        dto.setContent(todo.getContent());
                        dto.setPriorityType(todo.getPriorityType());
                        dto.setCreateTime(todo.getCreateTime());
                        return dto;
                    })
                    .toList();
        }

        dtoUser.setTodos(dtoTodos);
        return dtoUser;
    }

    @Override
    @Transactional
    public DtoUser updateUserInfo(DtoUserIU request) {
        User user = securityUtil.getAuthenticatedUser();


        userRepository.findByUsername(request.getUsername())
                .filter(existingUser -> !existingUser.getId().equals(user.getId()))
                .ifPresent(existingUser -> {
                    throw new BaseException(new ErrorMessage(
                            ErrorMessageType.USERNAME_ALREADY_EXISTS,
                            "Bu kullanıcı adı zaten kullanılıyor."
                    ));
                });

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGenderType(request.getGenderType());

        User updatedUser = userRepository.save(user);

        DtoUser dtoUser = new DtoUser();

        BeanUtils.copyProperties(updatedUser,dtoUser);

        return dtoUser;
    }

    @Override
    @Transactional
    public boolean deleteUser() {

        User user = securityUtil.getAuthenticatedUser();

        if (user == null) {
            throw new BaseException(new ErrorMessage(
                    ErrorMessageType.GENERAL_EXCEPTION,
                    "Kullanıcı bulunamadı veya oturum açmamış."
            ));
        }


        userRepository.delete(user);

        return true;
    }
}