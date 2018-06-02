package com.vibent.vibentback;

import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectedUserUtils {

    private UserRepository userRepository;

    public String getConnectedUserRef(){
        return  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User getConnectedUser(){
        return userRepository.findByRef(getConnectedUserRef())
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
    }
}
