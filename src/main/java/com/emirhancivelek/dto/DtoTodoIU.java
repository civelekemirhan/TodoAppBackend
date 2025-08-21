package com.emirhancivelek.dto;

import com.emirhancivelek.enums.PriorityType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoTodoIU {

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotNull
    private PriorityType priorityType;
    @NotNull
    private Long groupId;


}
