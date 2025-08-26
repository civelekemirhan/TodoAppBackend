package com.emirhancivelek.repository;

import com.emirhancivelek.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
    List<Group> findByUserId(Long userId);
}
