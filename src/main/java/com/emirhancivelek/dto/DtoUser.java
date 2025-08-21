package com.emirhancivelek.dto;

import com.emirhancivelek.enums.GenderType;
import com.emirhancivelek.model.Todo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoUser extends BaseDtoModel{


    @NotEmpty
    private String username;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotNull
    private GenderType genderType;
    @NotEmpty
    private List<DtoTodo> todos;



}
