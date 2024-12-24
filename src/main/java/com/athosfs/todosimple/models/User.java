package com.athosfs.todosimple.models;

import com.athosfs.todosimple.models.enums.ProfileEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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

  @JsonIgnore
  @OneToMany(mappedBy = "user") // um usuario tem varias tasks
  @JsonProperty(access = Access.WRITE_ONLY)
  private List<Task> tasks = new ArrayList<>();

  @ElementCollection(fetch = FetchType.EAGER)
  @JsonProperty(access = Access.WRITE_ONLY)
  @CollectionTable(name = "user_profiles")
  @Column(name = "profile", nullable = false)
  private Set<Integer> profiles = new HashSet<>();

  public Set<ProfileEnum> getProfiles() {
    return this.profiles.stream().map(ProfileEnum::toEnum).collect(Collectors.toSet());
  }

  public void addProfile(ProfileEnum profile) {
    this.profiles.add(profile.getCod());
  }
}
