package com.gitlab.vibent.vibentback.event;

import lombok.Data;
import lombok.NonNull;
import javax.persistence.*;
import java.util.Date;


@Data
@Entity
public class Event {

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

}