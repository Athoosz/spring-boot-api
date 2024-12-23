package com.athosfs.todosimple.services;

import com.athosfs.todosimple.models.*;
import com.athosfs.todosimple.repositories.*;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
  @Autowired private TaskRepository taskRepository;
  @Autowired private UserService userService;

  public Task findById(Long id) {
    Optional<Task> task = this.taskRepository.findById(id);
    return task.orElseThrow(() -> new RuntimeException("Tarefa nao encontrada, Id: " + id));
  }

  public List<Task> findAllByUserId(Long userId) {
    List<Task> tasks = this.taskRepository.findByUserId(userId);
    return tasks;
  }

  @Transactional
  public Task create(Task obj) {
    User user = this.userService.findById(obj.getUser().getId());
    obj.setId(null);
    obj.setUser(user);
    obj = this.taskRepository.save(obj);
    return obj;
  }

  @Transactional
  public Task update(Task obj) {
    Task newObj = this.findById(obj.getId());
    newObj.setTitle(obj.getTitle());
    newObj.setDescription(obj.getDescription());
    return this.taskRepository.save(newObj);
  }

  @Transactional
  public void delete(Long id) {
    this.findById(id);
    try {
      this.taskRepository.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException("Tarefa nao pode ser deletada");
    }
  }
}
