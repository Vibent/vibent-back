package com.vibent.vibentback.bubble.location;

import com.vibent.vibentback.bubble.Bubble;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE location_bubble SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class LocationBubble  extends Bubble {

    @NonNull
    private String coord;

}