package com.athosfs.todosimple.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Task.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
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
  @NotBlank
  @Size(min = 4, max = 100)
  private String title;

  @Column(name = "description", length = 300, nullable = false)
  @NotBlank
  @Size(min = 4, max = 300)
  private String description;
}
