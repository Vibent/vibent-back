
package com.gitlab.vibent.vibentback.groupInviteLink;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class GroupInviteLink {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String groupRef;
    @NonNull
    private String hash;
    @NonNull
    private Date expires;
    
}