package com.vibent.vibentback.auth;

import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.io.Serializable;

@Slf4j
@Component
@AllArgsConstructor
public class VibentPermissionEvaluator implements PermissionEvaluator {

    private EntityManager em;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.error("Called non implemented hasPermission method");
        throw new VibentException(VibentError.NOT_IMPLEMENTED);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return true; /*
        log.info("Determining if user {} can {} {} with id {}", authentication.getPrincipal(), permission, targetType, targetId);
        Optional<User> user = null; // TODO
        if (!user.isPresent())
            return false;
        long userId = user.get().getId();
        try {
            switch (targetType) {
                case "AlimentationBubble":
                    return true;
                    // return AlimentationBubblePermissionCheck(userId, (Long) targetId);
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
        */
    }
}
