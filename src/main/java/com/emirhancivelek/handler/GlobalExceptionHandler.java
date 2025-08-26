package com.emirhancivelek.handler;

import com.emirhancivelek.exception.BaseException;
import com.emirhancivelek.model.RootEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<RootEntity<?>> handleBaseException(BaseException ex, WebRequest webRequest) {
        ApiError<String> apiError = createApiError(ex.getMessage(), webRequest);
        return ResponseEntity.badRequest().body(RootEntity.error(apiError));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RootEntity<?>> handleValidationException(MethodArgumentNotValidException ex, WebRequest webRequest) {
        Map<String, List<String>> map = new HashMap<>();

        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) objectError).getField();
            map.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(objectError.getDefaultMessage());
        }

        ApiError<Map<String, List<String>>> apiError = createApiError(map, webRequest);
        return ResponseEntity.badRequest().body(RootEntity.error(apiError));
    }

    private <T> ApiError<T> createApiError(T message, WebRequest webRequest) {
        ApiError<T> apiError = new ApiError<>();
        Exception<T> exceptionDetail = new Exception<>();

        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionDetail.setHostName(getHostName());
        exceptionDetail.setMessage(message);
        exceptionDetail.setCreateTime(new Date());
        exceptionDetail.setPath(webRequest.getDescription(false).substring(4));

        apiError.setException(exceptionDetail);

        return apiError;
    }

    private String getHostName() {
        try {
            return Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }
}
