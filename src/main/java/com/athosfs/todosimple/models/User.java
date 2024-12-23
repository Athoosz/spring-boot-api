package com.athosfs.todosimple.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = User.TABLE_NAME)
public class User {

  public interface CreateUser {}

  public interface UpdateUser {}

  public static final String TABLE_NAME = "user";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @Column(name = "username", unique = true, length = 50, nullable = false)
  @NotNull(groups = CreateUser.class)
  @NotEmpty(groups = CreateUser.class)
  @Size(groups = CreateUser.class, min = 4, max = 50)
  private String username;

  @JsonProperty(access = Access.WRITE_ONLY)
  @Column(name = "password", length = 60, nullable = false)
  @NotNull(groups = {CreateUser.class, UpdateUser.class})
  @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
  @Size(
      groups = {CreateUser.class, UpdateUser.class},
      min = 4,
      max = 60)
  private String password;

  @OneToMany(mappedBy = "user") // um usuario tem varias tasks
  private List<Task> tasks = new ArrayList<>();

  public User() {}

  public User(Long id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    result = prime * result + ((password == null) ? 0 : password.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    User other = (User) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (username == null) {
      if (other.username != null) return false;
    } else if (!username.equals(other.username)) return false;
    if (password == null) {
      if (other.password != null) return false;
    } else if (!password.equals(other.password)) return false;
    return true;
  }
}
