package com.emirhancivelek.model;


import com.emirhancivelek.enums.PriorityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo extends BaseModel {

    @Column(name="title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "priority_type")
    @Enumerated(EnumType.STRING)
    private PriorityType priorityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "isCompleted")
    private boolean isCompleted;

}
