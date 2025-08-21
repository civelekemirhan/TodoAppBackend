package com.emirhancivelek.controller.impl;

import com.emirhancivelek.controller.IRestTodoController;
import com.emirhancivelek.dto.DtoTodo;
import com.emirhancivelek.dto.DtoTodoIU;
import com.emirhancivelek.model.RootEntity;
import com.emirhancivelek.service.ITodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class RestTodoControllerImpl extends RestBaseController implements IRestTodoController {

    @Autowired
    private ITodoService todoService;

    @Override
    @PostMapping("/save")
    public RootEntity<DtoTodo> saveTodo(@Valid @RequestBody DtoTodoIU input) {
        return ok(todoService.saveTodo(input));
    }

    @Override
    @GetMapping("/getTodos")
    public RootEntity<List<DtoTodo>> getAllTodoCurrentUser() {
        return ok(todoService.getAllTodoCurrentUser());
    }

    @Override
    @GetMapping("/getTodos/{groupId}")
    public List<DtoTodo> getAllTodoCurrentUserByGroup(@PathVariable(name = "groupId") Long groupId) {
        return todoService.getAllTodoCurrentUserByGroup(groupId);
    }

    @Override
    @PutMapping("/updateTodo/{todoId}")
    public RootEntity<DtoTodo> updateTodo(@PathVariable(name = "todoId") Long todoId, @Valid @RequestBody DtoTodoIU input) {
        return ok(todoService.updateTodo(todoId,input));
    }

    @Override
    @DeleteMapping("/delete/{todoId}")
    public boolean deleteTodo(@PathVariable(name = "todoId") Long todoId) {
        return todoService.deleteTodo(todoId);
    }
}
