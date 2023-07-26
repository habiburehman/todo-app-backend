package com.example.backend.Repositories;

import com.example.backend.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // You can define custom query methods here if needed
    @Query("select t from Task t where t.user.id = :userId")
    List<Task> findByUserId(Long userId);
    @Query("select t from Task t where t.id = :id and t.user.id = :userId")
    Task findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Query("delete from Task t where t.id = :id and t.user.id = :userId")
    void deleteByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

}