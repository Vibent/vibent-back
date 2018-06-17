package com.vibent.vibentback.api.group;

import com.vibent.vibentback.group.GroupT;
import lombok.Data;

@Data
public class PublicGroupResponse {

    private String name;
    private String ref;

    public PublicGroupResponse(GroupT group){
        this.name = group.getName();
        this.ref = group.getRef();
    }
}
