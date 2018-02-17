package com.vibent.vibentback.bubble.alimentation.bring;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE alimentation_bring SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class AlimentationBring {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @JsonIgnore // ignore as when returned it is already in entry object
    private Long entryId;
    @NonNull
    private String userRef;
    private int quantity;
    @Column(insertable = false, updatable = false)
    @JsonIgnore
    private boolean deleted;

}