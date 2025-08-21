package com.emirhancivelek.exception;

import com.emirhancivelek.enums.ErrorMessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private ErrorMessageType messageType;

    private String ofStatic;

    public String prepareErrorMessage(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(messageType);
        if(ofStatic != null){
            stringBuilder.append(" : "+ ofStatic);
        }

        return stringBuilder.toString();
    }


}
