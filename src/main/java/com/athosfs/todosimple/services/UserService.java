package com.athosfs.todosimple.services;

import com.athosfs.todosimple.models.*;
import com.athosfs.todosimple.models.dto.UserCreateDTO;
import com.athosfs.todosimple.models.dto.UserUpdateDTO;
import com.athosfs.todosimple.models.enums.ProfileEnum;
import com.athosfs.todosimple.repositories.*;
import com.athosfs.todosimple.security.UserSpringSecurity;
import com.athosfs.todosimple.services.exceptions.AuthorizationException;
import com.athosfs.todosimple.services.exceptions.DataBindingViolationException;
import com.athosfs.todosimple.services.exceptions.ObjectNotFoundException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired private UserRepository userRepository;

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User findById(Long id) {
    UserSpringSecurity userSpringSecurity = authenticated();
    if (Objects.nonNull(userSpringSecurity) && !userSpringSecurity.hasRole(ProfileEnum.ADMIN)
        && !id.equals(userSpringSecurity.getId())) {
      throw new AuthorizationException("Acesso negado");
    }
    Optional<User> user = this.userRepository.findById(id);
    return user.orElseThrow(() -> new ObjectNotFoundException("Usuario nao encontrado, Id: " + id));
  }

  @Transactional
  public User create(User obj) {
    obj.setId(null);
    obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
    obj.setProfiles(Stream.of(ProfileEnum.USER.getCod()).collect(Collectors.toSet()));
    obj = this.userRepository.save(obj);
    return obj;
  }

  @Transactional
  public User update(User obj) {
    User newObj = this.findById(obj.getId());
    newObj.setPassword(obj.getPassword());
    newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
    return this.userRepository.save(newObj);
  }

  @Transactional
  public void delete(Long id) {
    this.findById(id);
    try {
      this.userRepository.deleteById(id);
    } catch (Exception e) {
      throw new DataBindingViolationException(
          "Usuario possui tarefas associadas, nao pode ser deletado");
    }
  }

  public static UserSpringSecurity authenticated() {
    try {
      return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    } catch (Exception e) {
      return null;
    }
  }
  
   public User fromDTO(@Valid UserCreateDTO obj) {
        User user = new User();
        user.setUsername(obj.getUsername());
        user.setPassword(obj.getPassword());
        return user;
    }

    public User fromDTO(@Valid UserUpdateDTO obj) {
        User user = new User();
        user.setId(obj.getId());
        user.setPassword(obj.getPassword());
        return user;
    }

}
