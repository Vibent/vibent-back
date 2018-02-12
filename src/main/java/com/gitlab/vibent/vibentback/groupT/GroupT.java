package com.gitlab.vibent.vibentback.groupT;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class GroupT {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String ref;
    @NonNull
    private String name;
    private String imagePath;
    private boolean hasDefaultAdmin;

}