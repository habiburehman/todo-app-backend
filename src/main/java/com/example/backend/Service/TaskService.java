package com.example.backend.Service;

import com.example.backend.Models.Task;
import com.example.backend.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task addTask(Task task) {
        // Perform any additional logic before saving the task if needed
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask != null) {
            // Update the existing task with the new task properties
            existingTask.setDescription(task.getDescription());
            // ... update other properties as needed

            return taskRepository.save(existingTask);
        } else {
            return null;
        }
    }

    public boolean deleteTask(Long id) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask != null) {
            taskRepository.delete(existingTask);
            return true;
        } else {
            return false;
        }
    }
}
