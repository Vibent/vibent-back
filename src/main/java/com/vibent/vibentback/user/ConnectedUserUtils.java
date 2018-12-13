package com.vibent.vibentback.user;

import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectedUserUtils {

    private UserRepository userRepository;

    public String getConnectedUserRef() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User getConnectedUser() {
        return userRepository.findByRef(getConnectedUserRef())
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
    }

    public User updateConnectedUser(){
        return userRepository.save(getConnectedUser());
    }
}
