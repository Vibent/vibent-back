package com.vibent.vibentback.bubble.planning.entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE planning_entry SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class PlanningEntry {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Long bubbleId;
    @NonNull
    private String creatorRef;
    @NonNull
    private Date start;
    @NonNull
    private Date end;
    @NonNull
    private String content;
    @Column(insertable = false, updatable = false)
    @JsonIgnore
    private boolean deleted;


}