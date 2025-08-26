package com.emirhancivelek.dto;

import com.emirhancivelek.enums.PriorityType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoTodo extends BaseDtoModel{


    private String title;

    private String content;

    private PriorityType priorityType;

    private String groupName;

    private boolean isCompleted;
}
