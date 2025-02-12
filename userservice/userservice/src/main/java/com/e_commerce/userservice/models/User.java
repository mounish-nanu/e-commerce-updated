package com.e_commerce.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseUser {
   private String password;
   @OneToMany(mappedBy = "user")
   private List<Sessions> sessions = new ArrayList<>();
}
