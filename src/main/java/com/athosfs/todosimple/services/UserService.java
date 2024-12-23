package com.athosfs.todosimple.services;

import com.athosfs.todosimple.models.*;
import com.athosfs.todosimple.repositories.*;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User findById(Long id) {
    Optional<User> user = this.userRepository.findById(id);
    return user.orElseThrow(() -> new RuntimeException("Usuario nao encontrado, Id: " + id));
  }

  @Transactional
  public User create(User obj) {
    obj.setId(null);
    obj = this.userRepository.save(obj);
    return obj;
  }
  
  @Transactional
  public User update(User obj) {
    User newObj = this.findById(obj.getId());
    newObj.setPassword(obj.getPassword());
    return this.userRepository.save(newObj);
  }

  @Transactional
  public void delete(Long id) {
    this.findById(id);                            
    try{
      this.userRepository.deleteById(id);
    }
    catch(Exception e){
      throw new RuntimeException("Usuario possui tarefas associadas, nao pode ser deletado");
    }
  }


}
