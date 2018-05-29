package com.vibent.vibentback.auth.permission;

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
        log.info("Determining if user {} has {} on {} with id {}", authentication.getPrincipal(), permission, targetType, targetId);
        if (authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof String))
            return false;
        if (permission == null || !(permission instanceof String))
            return false;
        String ref = (String) authentication.getPrincipal();
        try {
            switch (targetType) {
                case "AlimentationBubble":
                    if (permission.equals("ROLE_USER"))
                        return userAlimentationBubblePermissionCheck(ref, (Long) targetId);
                    else if (permission.equals("ROLE_USER"))
                        return adminAlimentationBubblePermissionCheck(ref, (Long) targetId);
                    return false;
                case "AlimentationEntry":
                    if (permission.equals("ROLE_USER"))
                        return userAlimentationEntryPermissionCheck(ref, (Long) targetId);
                case "AlimentationBring":
                    if (permission.equals("ROLE_USER"))
                        return userAlimentationBringPermissionCheck(ref, (Long) targetId);
                case "Event":
                    if (permission.equals("ROLE_ADMIN"))
                        return adminEventPermissionCheck(ref, (String) targetId);
                default:
                    return false;
            }
        } catch (Exception e) {
            log.error("{}", e);
            return false;
        }
    }

    private boolean userAlimentationBubblePermissionCheck(String ref, Long targetId) {
        String sql = "SELECT CASE WHEN EXISTS( SELECT u.id FROM user u JOIN group_membership gm ON gm.user_id = u.id JOIN group_t g ON gm.group_id = g.id JOIN event e ON g.id = e.group_id JOIN alimentation_bubble ab ON e.id = ab.event_id WHERE ab.id = :targetId AND u.ref = :ref ) THEN 1 ELSE 0 END AS is_authorized;";
        Permission permission = (Permission) em.createNativeQuery(sql, Permission.class)
                .setParameter("targetId", targetId)
                .setParameter("ref", ref)
                .getSingleResult();
        return permission.isAuthorized;
    }

    private boolean adminAlimentationBubblePermissionCheck(String ref, Long targetId) {
        String sql = "SELECT CASE WHEN EXISTS( SELECT u.id FROM user u JOIN group_membership gm ON gm.user_id = u.id JOIN group_t g ON gm.group_id = g.id JOIN event e ON g.id = e.group_id JOIN alimentation_bubble ab ON e.id = ab.event_id WHERE ab.id = :targetId AND u.ref = :ref AND g.has_default_admin = TRUE) THEN TRUE ELSE CASE WHEN EXISTS( SELECT u.id FROM user u JOIN group_adminship ga ON ga.user_id = u.id JOIN group_t g ON ga.group_id = g.id JOIN event e ON g.id = e.group_id JOIN alimentation_bubble ab ON e.id = ab.event_id WHERE ab.id = :targetId AND u.ref = :ref ) THEN TRUE ELSE FALSE END END AS is_authorized;";
        Permission permission = (Permission) em.createNativeQuery(sql, Permission.class)
                .setParameter("targetId", targetId)
                .setParameter("ref", ref)
                .getSingleResult();
        return permission.isAuthorized;
    }

    private boolean userAlimentationEntryPermissionCheck(String ref, Long targetId) {
        String sql = "SELECT CASE WHEN EXISTS( SELECT u.id FROM user u JOIN group_membership gm ON gm.user_id = u.id JOIN group_t g ON gm.group_id = g.id JOIN event e ON g.id = e.group_id JOIN alimentation_bubble ab ON e.id = ab.event_id JOIN alimentation_entry ae ON ab.id = ae.bubble_id WHERE ae.id = :targetId AND u.ref = :ref) THEN TRUE ELSE FALSE END AS is_authorized;";
        Permission permission = (Permission) em.createNativeQuery(sql, Permission.class)
                .setParameter("targetId", targetId)
                .setParameter("ref", ref)
                .getSingleResult();
        return permission.isAuthorized;
    }

    private boolean userAlimentationBringPermissionCheck(String ref, Long targetId) {
        String sql = "SELECT CASE WHEN EXISTS( SELECT u.id FROM user u JOIN group_membership gm ON gm.user_id = u.id JOIN group_t g ON gm.group_id = g.id JOIN event e ON g.id = e.group_id JOIN alimentation_bubble ab ON e.id = ab.event_id JOIN alimentation_entry ae ON ab.id = ae.bubble_id JOIN alimentation_bring ab ON ae.id = ab.entry_id WHERE ab.id=:targetId AND u.ref=:ref) THEN TRUE ELSE FALSE END AS is_authorized;";
        Permission permission = (Permission) em.createNativeQuery(sql, Permission.class)
                .setParameter("targetId", targetId)
                .setParameter("ref", ref)
                .getSingleResult();
        return permission.isAuthorized;
    }

    private boolean adminEventPermissionCheck(String ref, String eventRef) {
        String sql = "SELECT CASE WHEN EXISTS( SELECT u.id FROM user u JOIN group_membership gm ON gm.user_id = u.id JOIN group_t g ON gm.group_id = g.id JOIN event e ON g.id = e.group_id WHERE e.ref = :eventRef AND u.ref = :ref AND g.has_default_admin = TRUE) THEN TRUE ELSE CASE WHEN EXISTS( SELECT u.id FROM user u JOIN group_adminship ga ON ga.user_id = u.id JOIN group_t g ON ga.group_id = g.id JOIN event e ON g.id = e.group_id WHERE e.ref = :eventRef AND u.ref = :ref ) THEN TRUE ELSE FALSE END END AS is_authorized;";
        Permission permission = (Permission) em.createNativeQuery(sql, Permission.class)
                .setParameter("eventRef", eventRef)
                .setParameter("ref", ref)
                .getSingleResult();
        return permission.isAuthorized;
    }
}
