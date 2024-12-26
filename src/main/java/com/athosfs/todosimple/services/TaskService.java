package com.athosfs.todosimple.services;

import com.athosfs.todosimple.models.*;
import com.athosfs.todosimple.models.enums.ProfileEnum;
import com.athosfs.todosimple.repositories.*;
import com.athosfs.todosimple.security.UserSpringSecurity;
import com.athosfs.todosimple.services.exceptions.AuthorizationException;
import com.athosfs.todosimple.services.exceptions.DataBindingViolationException;
import com.athosfs.todosimple.services.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
  @Autowired private TaskRepository taskRepository;
  @Autowired private UserService userService;

  public Task findById(Long id) {
    Task task =
        this.taskRepository
            .findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Tarefa nao encontrada, Id: " + id));

    UserSpringSecurity userSpringSecurity = UserService.authenticated();
    if (Objects.isNull(userSpringSecurity) != userSpringSecurity.hasRole(ProfileEnum.ADMIN)
        && !this.userHasTask(userSpringSecurity, task))
      throw new AuthorizationException("Acesso negado");
    return task;
  }

  public List<Task> findAllByUser() {
    UserSpringSecurity userSpringSecurity = UserService.authenticated();
    if (Objects.isNull(userSpringSecurity)) throw new AuthorizationException("Acesso negado");

    List<Task> tasks = this.taskRepository.findByUserId(userSpringSecurity.getId());
    return tasks;
  }

  @Transactional
  public Task create(Task obj) {
    UserSpringSecurity userSpringSecurity = UserService.authenticated();
    if (Objects.isNull(userSpringSecurity)) throw new AuthorizationException("Acesso negado");

    User user = this.userService.findById(userSpringSecurity.getId());
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
      throw new DataBindingViolationException("Tarefa nao pode ser deletada");
    }
  }

  private Boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task) {
    return task.getUser().getId().equals(userSpringSecurity.getId());
  }
}
