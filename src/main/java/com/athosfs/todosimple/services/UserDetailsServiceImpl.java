package com.athosfs.todosimple.services;

import com.athosfs.todosimple.models.User;
import com.athosfs.todosimple.repositories.UserRepository;
import com.athosfs.todosimple.security.UserSpringSecurity;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = this.userRepository.findByUsername(username);
    if (Objects.isNull(user))
      throw new UsernameNotFoundException("Usuário não encontrado: " + username);
    return new UserSpringSecurity(
        user.getId(), user.getUsername(), user.getPassword(), user.getProfiles());
  }
}
