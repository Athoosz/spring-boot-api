package com.athosfs.todosimple.models.enums;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProfileEnum {
  ADMIN(1, "ROLE_ADMIN"),
  USER(2, "ROLE_USER");

  private int cod;
  private String description;

  public static ProfileEnum toEnum(Integer cod) {
    if (Objects.isNull(cod)) {
      return null;
    }

    for (ProfileEnum x : ProfileEnum.values()) {
      if (cod.equals(x.getCod())) {
        return x;
      }
    }

    throw new IllegalArgumentException("Id invalido: " + cod);
  }
}
