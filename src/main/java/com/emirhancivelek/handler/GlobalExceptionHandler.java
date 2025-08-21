package com.emirhancivelek.handler;

import com.emirhancivelek.exception.BaseException;
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
    public  ResponseEntity<ApiError<?>> handlerBaseException(BaseException ex, WebRequest webRequest){
         return  ResponseEntity.badRequest().body(createApiError(ex.getMessage(),webRequest));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest){
        Map<String, List<String>> map =new HashMap<>();

        for(ObjectError objectError : ex.getBindingResult().getAllErrors()){
            String fieldName = ((FieldError)objectError).getField();
            if(map.containsKey(fieldName)){
                map.put(fieldName,addValue(map.get(fieldName),objectError.getDefaultMessage()));
            }else{
                map.put(fieldName,addValue(new ArrayList<>(),objectError.getDefaultMessage()));
            }
        }

        return ResponseEntity.badRequest().body(createApiError(map,webRequest));

    }

    private List<String> addValue(List<String> list , String newValue){
        list.add(newValue);
        return list;
    }

    private <T> ApiError<T> createApiError(T message,WebRequest webRequest){

        ApiError<T> apiError =new ApiError<>();
        Exception<T> exception = new Exception<>();

        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        exception.setHostName(getHostName());
        exception.setMessage(message);
        exception.setCreateTime(new Date());
        exception.setPath(webRequest.getDescription(false).substring(4));

        apiError.setException(exception);

        return apiError;

    }

    private String getHostName(){
        try {
            return Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";

    }

}
