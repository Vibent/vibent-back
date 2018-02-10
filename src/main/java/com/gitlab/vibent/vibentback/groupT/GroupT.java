package com.gitlab.vibent.vibentback.groupT;

import lombok.Data;
import lombok.NonNull;
import javax.persistence.*;


@Data
@Entity
@Table(name = "groupT")
public class GroupT {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String ref;
    @NonNull
    private String name;
    private String imagePath;
    @NonNull
    private boolean hasDefaultAdmin;

}