package com.example.backend.Controllers;

import com.example.backend.Models.Task;
import com.example.backend.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/todo")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@AuthenticationPrincipal Jwt principal) {
        Map<String, Object> claims = principal.getClaims();
        Long userId = (Long) claims.get("userId");
        System.out.print(userId);
        List<Task> tasks = taskService.getAllTasks(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@AuthenticationPrincipal Jwt principal, @PathVariable Long id) {
        Map<String, Object> claims = principal.getClaims();
        Long userId = (Long) claims.get("userId");
        Task task = taskService.getTaskById(id, userId);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/task")
    public ResponseEntity<?> addTask(@AuthenticationPrincipal Jwt principal, @Valid @RequestBody Task task, BindingResult bindingResult) {
        Map<String, Object> claims = principal.getClaims();
        Long userId = (Long) claims.get("userId");
        if (bindingResult.hasErrors() || task.getDescription() == null) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            errorMap.put("description", "Description is required");

            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }

        Task createdTask = taskService.addTask(task, userId);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@AuthenticationPrincipal Jwt principal, @PathVariable Long id, @RequestBody Task task) {
        Map<String, Object> claims = principal.getClaims();
        Long userId = (Long) claims.get("userId");
        Task updatedTask = taskService.updateTask(id, task, userId);
        if (updatedTask != null) {
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal Jwt principal, @PathVariable Long id) {
        Map<String, Object> claims = principal.getClaims();
        Long userId = (Long) claims.get("userId");
        boolean deleted = taskService.deleteTask(id, userId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
