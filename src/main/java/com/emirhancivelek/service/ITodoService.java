package com.emirhancivelek.service;

import com.emirhancivelek.dto.DtoTodo;
import com.emirhancivelek.dto.DtoTodoIU;

import java.util.List;

public interface ITodoService {

    public DtoTodo saveTodo(DtoTodoIU input);

    public DtoTodo updateTodo(Long todoId,DtoTodoIU input);

    public List<DtoTodo> getAllTodoCurrentUser();

    public List<DtoTodo> getAllTodoCurrentUserByGroup(Long groupId);

    public boolean deleteTodo(Long todoId);

}
