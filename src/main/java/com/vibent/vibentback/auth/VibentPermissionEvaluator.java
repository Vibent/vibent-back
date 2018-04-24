package com.vibent.vibentback.auth;

import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.math.BigInteger;

@Slf4j
@Component
@AllArgsConstructor
public class VibentPermissionEvaluator implements PermissionEvaluator {

    @PersistenceContext
    private EntityManager em;

    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.error("Called non implemented hasPermission method");
        throw new VibentException(VibentError.NOT_IMPLEMENTED);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        log.info("Determining if user {} can {} {} with id {}", authentication.getPrincipal(), permission, targetType, targetId);
        if(authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof String))
            return false;
        String username = (String) authentication.getPrincipal();
        try {
            switch (targetType) {
                case "AlimentationBubble":
                    return alimentationBubblePermissionCheck(username, (Long) targetId);
                default:
                    return false;
            }
        } catch (Exception e) {
            log.error("{}", e);
            return false;
        }
    }

    private boolean alimentationBubblePermissionCheck(String username, Long targetId) {
        BigInteger result = (BigInteger) em.createNativeQuery("SELECT CASE WHEN EXISTS( SELECT u.id FROM user u JOIN group_membership gm ON gm.user_id = u.id JOIN group_t g ON gm.group_id = g.id JOIN event e ON g.id = e.group_id JOIN alimentation_bubble ab ON e.id = ab.event_id WHERE ab.id = :targetId AND u.username = :username ) THEN 1 ELSE 0 END")
                .setParameter("targetId", targetId)
                .setParameter("username", username)
                .getSingleResult();
        return result.signum() == 1;
    }
}
