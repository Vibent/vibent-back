package com.vibent.vibentback.bubble.free;

import com.vibent.vibentback.bubble.Bubble;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.Entity;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE free_bubble SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class FreeBubble extends Bubble {

    @NonNull
    private String title;

    private String content;
}