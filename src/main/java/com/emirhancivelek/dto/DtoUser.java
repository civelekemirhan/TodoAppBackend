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



    private String username;

    private String firstName;

    private String lastName;

    private GenderType genderType;

    private List<DtoTodo> todos;



}
