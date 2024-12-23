package com.athosfs.todosimple.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = Task.TABLE_NAME)
public class Task {
  public static final String TABLE_NAME = "task";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @ManyToOne // varias tasks pertencem a um usuario
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @Column(name = "title", length = 100, nullable = false)
  @NotNull
  @NotEmpty
  @Size(min = 4, max = 100)
  private String title;

  @Column(name = "description", length = 300, nullable = false)
  @NotNull
  @NotEmpty
  @Size(min = 4, max = 300)
  private String description;

  public Task() {}

  public Task(
      Long id,
      User user,
      @NotNull @NotEmpty @Size(min = 4, max = 100) String title,
      @NotNull @NotEmpty @Size(min = 4, max = 300) String description) {
    this.id = id;
    this.user = user;
    this.title = title;
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
