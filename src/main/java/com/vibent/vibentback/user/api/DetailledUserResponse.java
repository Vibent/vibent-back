package com.vibent.vibentback.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vibent.vibentback.auth.social.provider.Provider;
import com.vibent.vibentback.event.api.UserParticipationResponse;
import com.vibent.vibentback.user.User;
import lombok.Data;

import java.util.*;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class DetailledUserResponse {
    @JsonIgnore
    private User user;

    public DetailledUserResponse(User user) {
        this.user = user;
    }

    @JsonProperty
    public String getRef() {
        return user.getRef();
    }

    @JsonProperty
    public String getEmail() {
        return user.getEmail();
    }

    @JsonProperty
    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }

    @JsonProperty
    public String getFirstName() {
        return user.getFirstName();
    }

    @JsonProperty
    public String getLastName() {
        return user.getLastName();
    }

    @JsonProperty
    public String getProfilePicLocation() {
        return user.getProfilePicLocation();
    }

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    public Date getLastLogin() {
        return user.getLastLogin();
    }

    @JsonProperty
    public Set<UserParticipationResponse> getParticipations() {
        Set<UserParticipationResponse> participationResponses = new HashSet<>();
        user.getParticipations().forEach(e ->
                participationResponses.add(new UserParticipationResponse(e.getEventRef(), e.getAnswer())));
        return participationResponses;
    }

    @JsonProperty
    public Map<Provider, Boolean> getSocialCredentials() {
        Map<Provider, Boolean> connectedSocialCredentials = new HashMap<>();
        Arrays.stream(Provider.values()).forEach(p -> {
            boolean exists = user.getCredentials().stream().anyMatch(c -> c.getProvider().equals(p));
            connectedSocialCredentials.put(p, exists);
        });
        return connectedSocialCredentials;
    }
}
