package com.gitlab.vibent.vibentback.user;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String ref;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String email;
    private String imagePath;
    @NonNull
    private String password;
    @NonNull
    private String salt;
}