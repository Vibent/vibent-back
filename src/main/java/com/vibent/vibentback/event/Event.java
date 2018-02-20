package com.vibent.vibentback.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE event SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String ref;
    @NonNull
    private String groupRef;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private Date startDate;
    @NonNull
    private Date endDate;
    @Column(insertable = false, updatable = false)
    @JsonIgnore
    private boolean deleted;

}