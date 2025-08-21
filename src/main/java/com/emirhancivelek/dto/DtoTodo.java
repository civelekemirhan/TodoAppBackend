package com.emirhancivelek.dto;

import com.emirhancivelek.enums.PriorityType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoTodo extends BaseDtoModel{

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotNull
    private PriorityType priorityType;
    @NotEmpty
    private String groupName;
}
