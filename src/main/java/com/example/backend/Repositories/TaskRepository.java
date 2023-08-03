package com.example.backend.Repositories;

import com.example.backend.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // You can define custom query methods here if needed
    @Query("select t from Task t where t.userId = :userId")
    List<Task> findByUserId(Long userId);
    @Query("select t from Task t where t.id = :id and t.userId = :userId")
    Task findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("delete from Task t where t.id = :id and t.userId = :userId")
    void deleteByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

}