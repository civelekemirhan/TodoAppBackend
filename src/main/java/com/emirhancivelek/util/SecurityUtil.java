package com.emirhancivelek.util;

import com.emirhancivelek.enums.ErrorMessageType;
import com.emirhancivelek.exception.BaseException;
import com.emirhancivelek.exception.ErrorMessage;
import com.emirhancivelek.model.User;
import com.emirhancivelek.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private final UserRepository userRepository;

    public SecurityUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BaseException(new ErrorMessage(
                    ErrorMessageType.GENERAL_EXCEPTION,
                    "Kullanıcı oturumu bulunamadı."
            ));
        }

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(ErrorMessageType.USERNAME_NOT_FOUND, "User not found")
                ));
    }
}