package com.emirhancivelek.controller;

import com.emirhancivelek.dto.DtoTodo;
import com.emirhancivelek.dto.DtoTodoIU;
import com.emirhancivelek.model.RootEntity;

import java.util.List;

public interface IRestTodoController {

    public RootEntity<DtoTodo> saveTodo(DtoTodoIU input);
    public RootEntity<List<DtoTodo>> getAllTodoCurrentUser();
    public List<DtoTodo> getAllTodoCurrentUserByGroup(Long groupId);

    public RootEntity<DtoTodo> updateTodo(Long todoId,DtoTodoIU input);

    public boolean deleteTodo(Long todoId);


}
