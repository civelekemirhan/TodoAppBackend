package com.emirhancivelek.service.impl;

import com.emirhancivelek.dto.*;
import com.emirhancivelek.enums.ErrorMessageType;
import com.emirhancivelek.exception.BaseException;
import com.emirhancivelek.exception.ErrorMessage;
import com.emirhancivelek.jwt.JwtService;
import com.emirhancivelek.model.Group;
import com.emirhancivelek.model.RefreshToken;
import com.emirhancivelek.model.Todo;
import com.emirhancivelek.model.User;
import com.emirhancivelek.repository.GroupRepository;
import com.emirhancivelek.repository.RefreshTokenRepository;
import com.emirhancivelek.repository.UserRepository;
import com.emirhancivelek.service.IRestAuthenticationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.util.*;

@Service
public class RestAuthenticationServiceImpl implements IRestAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;




    @Override
    public DtoUser register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BaseException(new ErrorMessage(
                    ErrorMessageType.PASSWORDS_NOT_MATCH,
                    "password :" + request.getPassword() +
                            " confirmPassword :" + request.getConfirmPassword()
            ));
        }

        userRepository.findByUsername(request.getUsername())
                .ifPresent(existingUser -> {
                    throw new BaseException(new ErrorMessage(
                            ErrorMessageType.USERNAME_ALREADY_EXISTS,
                            "Bu kullanıcı adı zaten kullanılıyor."
                    ));
                });

        User user = new User();
        user.setCreateTime(new Date());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGenderType(request.getGenderType());
        user.setTodos(new ArrayList<>());


        userRepository.save(user);
        Group defaultGroup = new Group();
        defaultGroup.setGroupName("Default");
        defaultGroup.setCreateTime(new Date());
        defaultGroup.setUser(user);
        groupRepository.save(defaultGroup);

        DtoUser dtoUser = new DtoUser();
        BeanUtils.copyProperties(user, dtoUser);
        return dtoUser;
    }


    @Override
    public AuthenticateResponse authenticate(AuthenticateRequest request) {

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());

            authenticationProvider.authenticate(authenticationToken);
            Optional<User> optUser = userRepository.findByUsername(request.getUsername());
            String accessToken = jwtService.generateToken(optUser.get());
            RefreshToken refreshToken = refreshTokenRepository.save(createRefreshToken(optUser.get()));

            return new AuthenticateResponse(accessToken,refreshToken.getRefreshToken());


        }catch(Exception e){
            throw new BaseException(new ErrorMessage(ErrorMessageType.USERNAME_OR_PASSWORD_INVALID,e.getMessage()));
        }
    }

    public boolean isValidRefreshToken(Date expiredDate){
        return new Date().before(expiredDate);
    }

    @Override
    public AuthenticateResponse refreshToken(RefreshTokenRequest input) {

        Optional<RefreshToken> optionalRefreshToken=refreshTokenRepository.findByRefreshToken(input.getRefreshToken());
        if(optionalRefreshToken.isEmpty()){
            throw new BaseException(new ErrorMessage(ErrorMessageType.REFRESH_TOKEN_NOT_FOUNT,input.getRefreshToken()));
        }

        if(!isValidRefreshToken(optionalRefreshToken.get().getExpiredDate())){
            throw new BaseException(new ErrorMessage(ErrorMessageType.REFRESH_TOKEN_IS_EXPIRED,input.getRefreshToken()));
        }
        String accessToken = jwtService.generateToken(optionalRefreshToken.get().getUser());
        RefreshToken refreshToken = refreshTokenRepository.save(createRefreshToken(optionalRefreshToken.get().getUser()));

        return new AuthenticateResponse(accessToken,refreshToken.getRefreshToken());

    }

    private RefreshToken createRefreshToken(User user){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setCreateTime(new Date());
        refreshToken.setUser(user);
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis()+1000*60*60*4));

        return refreshToken;
    }

}
