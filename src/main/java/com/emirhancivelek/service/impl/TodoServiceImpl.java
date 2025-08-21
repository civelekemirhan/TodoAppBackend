package com.emirhancivelek.service.impl;

import com.emirhancivelek.dto.DtoTodo;
import com.emirhancivelek.dto.DtoTodoIU;
import com.emirhancivelek.enums.ErrorMessageType;
import com.emirhancivelek.exception.BaseException;
import com.emirhancivelek.exception.ErrorMessage;
import com.emirhancivelek.model.Group;
import com.emirhancivelek.model.Todo;
import com.emirhancivelek.model.User;
import com.emirhancivelek.repository.GroupRepository;
import com.emirhancivelek.repository.TodoRepository;
import com.emirhancivelek.repository.UserRepository;
import com.emirhancivelek.service.ITodoService;
import com.emirhancivelek.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TodoServiceImpl implements ITodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public DtoTodo saveTodo(DtoTodoIU input) {

        User user = securityUtil.getAuthenticatedUser();

        Optional<Group> group = groupRepository.findById(input.getGroupId());

        if(group.isEmpty()){
            throw new BaseException(new ErrorMessage(ErrorMessageType.GROUPS_IS_NULL,""));
        }

        Todo todo = new Todo();
        todo.setContent(input.getContent());
        todo.setTitle(input.getTitle());
        todo.setPriorityType(input.getPriorityType());
        todo.setCreateTime(new Date());
        todo.setGroup(group.get());
        todo.setUser(user);

        Todo savedTodo = todoRepository.save(todo);

        DtoTodo dtoTodo = new DtoTodo();
        BeanUtils.copyProperties(savedTodo, dtoTodo);
        dtoTodo.setGroupName(group.get().getGroupName());
        return dtoTodo;
    }

    @Override
    @Transactional
    public DtoTodo updateTodo(Long todoId, DtoTodoIU input) {
        User user = securityUtil.getAuthenticatedUser();

        if (todoId == null) {
            throw new BaseException(new ErrorMessage(
                    ErrorMessageType.GENERAL_EXCEPTION,
                    "Güncellenecek todo ID değeri boş olamaz."
            ));
        }

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(ErrorMessageType.TODO_NOT_FOUND, "Todo bulunamadı")
                ));

        if (!todo.getUser().getId().equals(user.getId())) {
            throw new BaseException(new ErrorMessage(
                    ErrorMessageType.GENERAL_EXCEPTION,
                    "Bu todo'yu güncelleme yetkiniz yok. Sizin değil"
            ));
        }

        todo.setTitle(input.getTitle());
        todo.setContent(input.getContent());
        todo.setPriorityType(input.getPriorityType());

        if (input.getGroupId() != null) {
            Group group = groupRepository.findById(input.getGroupId())
                    .orElseThrow(() -> new BaseException(
                            new ErrorMessage(ErrorMessageType.GROUPS_IS_NULL, "Grup bulunamadı")
                    ));
            todo.setGroup(group);
        }

        Todo updatedTodo = todoRepository.save(todo);

        DtoTodo dtoTodo = new DtoTodo();
        BeanUtils.copyProperties(updatedTodo, dtoTodo);
        dtoTodo.setGroupName(updatedTodo.getGroup().getGroupName());

        return dtoTodo;
    }


    @Override
    public List<DtoTodo> getAllTodoCurrentUser() {

        User user = securityUtil.getAuthenticatedUser();


        List<DtoTodo> dtoTodos = new ArrayList<>();

        for(Todo todo : user.getTodos()){
            DtoTodo dtoTodo = new DtoTodo();
            BeanUtils.copyProperties(todo,dtoTodo);
            dtoTodo.setGroupName(todo.getGroup().getGroupName());
            dtoTodos.add(dtoTodo);
        }


        return  dtoTodos;
    }

    @Override
    public List<DtoTodo> getAllTodoCurrentUserByGroup(Long groupId) {

        User user = securityUtil.getAuthenticatedUser();

        List<Todo> todos = todoRepository.findAllByUser_IdAndGroup_Id(user.getId(), groupId);

        if (todos.isEmpty()) {
            throw new BaseException(new ErrorMessage(
                    ErrorMessageType.GROUPS_IS_NULL,
                    "Bu gruba ait todo bulunamadı: " + groupId
            ));
        }

        Optional<Group> group = groupRepository.findById(groupId);


        List<DtoTodo> dtoTodos = new ArrayList<>();
        for (Todo todo : todos) {
            DtoTodo dtoTodo = new DtoTodo();
            if(group.isPresent()){
                dtoTodo.setGroupName(group.get().getGroupName());
            }else{
                dtoTodo.setGroupName("");
            }
            BeanUtils.copyProperties(todo, dtoTodo);
            dtoTodos.add(dtoTodo);
        }


        return dtoTodos;
    }

    @Override
    @Transactional
    public boolean deleteTodo(Long todoId) {
        User user = securityUtil.getAuthenticatedUser();

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(ErrorMessageType.TODO_NOT_FOUND, "Todo bulunamadı")
                ));

        if (!todo.getUser().getId().equals(user.getId())) {
            throw new BaseException(new ErrorMessage(
                    ErrorMessageType.GENERAL_EXCEPTION,
                    "Bu todo'yu silme yetkiniz yok."
            ));
        }
        todoRepository.delete(todo);
        return  true;
    }
}