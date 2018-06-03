package com.vibent.vibentback.auth;

import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
public class VibentAuthentication implements Authentication {


    private String userRef;
    private String encodedPassword;

    /**
     * Unused
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * @return The encoded password as proof of correct principal
     */
    @Override
    public Object getCredentials() {
        return encodedPassword;
    }

    /**
     * Unused
     */
    @Override
    public Object getDetails() {
        throw new VibentException(VibentError.NOT_IMPLEMENTED);
    }

    /**
     * @return The user reference of the authenticated user
     */
    @Override
    public Object getPrincipal() {
        return userRef;
    }

    /**
     * Unused
     */
    @Override
    public boolean isAuthenticated() {
        return true;
    }

    /**
     * Unused
     */
    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException { }

    /**
     * Unused
     */
    @Override
    public String getName() {
        return null;
    }
}
