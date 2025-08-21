package com.emirhancivelek.repository;

import com.emirhancivelek.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByUser_IdAndGroup_Id(Long userId, Long groupId);
    List<Todo> findAllByUser_Id(Long userId);

}