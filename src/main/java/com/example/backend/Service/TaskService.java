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

        public List<Task> getAllTasks(Long userId) {
            return taskRepository.findByUserId(userId);
        }

    public Task getTaskById(Long id, Long userId) {
        return taskRepository.findByIdAndUserId(id, userId);
    }

    public Task addTask(Task task, Long userId) {
        // Perform any additional logic before saving the task if needed
        task.setUserId(userId); // Set the userId of the task
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task, Long userId) {
        Task existingTask = taskRepository.findByIdAndUserId(id, userId);
        if (existingTask != null) {
            // Update the existing task with the new task properties
            existingTask.setDescription(task.getDescription());
            // ... update other properties as needed

            return taskRepository.save(existingTask);
        } else {
            return null;
        }
    }

    public boolean deleteTask(Long id, Long userId) {
        Task existingTask = taskRepository.findByIdAndUserId(id, userId);
        if (existingTask != null) {
            taskRepository.deleteByIdAndUserId(id, userId);
            return true;
        } else {
            return false;
        }
    }

}
