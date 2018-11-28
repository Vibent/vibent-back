package com.vibent.vibentback.user.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.user.User;


public class SimpleUserResponse {
    @JsonIgnore
    private User user;

    public SimpleUserResponse(User user){
        this.user = user;
    }

    @JsonProperty
    public String getRef(){
        return user.getRef();
    }

    @JsonProperty
    public String getFirstName(){
        return user.getFirstName();
    }

    @JsonProperty
    public String getLastName(){
        return user.getLastName();
    }

}
