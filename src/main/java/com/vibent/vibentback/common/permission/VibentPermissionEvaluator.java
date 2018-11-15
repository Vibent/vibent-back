package com.vibent.vibentback.common.permission;

import com.vibent.vibentback.user.ConnectedUserUtils;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.util.CrudRefRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class VibentPermissionEvaluator implements PermissionEvaluator {

    private ConnectedUserUtils userUtils;
    private Repositories repositories;

    /**
     * Translates short class names "Event" to fully qualified class names "com.event.[...].Event
     * Only contains entities managed by JPA
     */
    private Map<String, String> fullClassNames;

    @Autowired
    public VibentPermissionEvaluator(ConnectedUserUtils userUtils, WebApplicationContext webApplicationContext) {
        this.userUtils = userUtils;
        repositories = new Repositories(webApplicationContext);

        fullClassNames = new HashMap<>();
        repositories.forEach(r -> fullClassNames.put(r.getSimpleName(), r.getName()));
    }

    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.info("Determining if user ID {} has {} on {}", authentication.getPrincipal(), permission, targetDomainObject.getClass().getSimpleName());
        if (!(targetDomainObject instanceof Permissible) || !(permission instanceof String)) {
            log.error("TargetDomainObject or permission isn't correct type at permission check");
            throw new VibentException(VibentError.INTERNAL_SERVER_ERROR);
        }

        if (permission.equals("read")) {
            if (!((Permissible) targetDomainObject).canRead(userUtils.getConnectedUser())) {
                throw new VibentException(VibentError.FORBIDDEN);
            }
            return true;
        }

        if (permission.equals("write")) {
            if (!((Permissible) targetDomainObject).canWrite(userUtils.getConnectedUser())) {
                throw new VibentException(VibentError.FORBIDDEN);
            }
            return true;
        }

        log.error("Permission value isn't valid at permission check");
        throw new VibentException(VibentError.INTERNAL_SERVER_ERROR);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        Optional targetOpt = Optional.empty();
        Class targetClass;
        try {
            targetClass = Class.forName(fullClassNames.get(targetType));
        } catch (ClassNotFoundException e) {
            log.error("Couldn't find class for entity at permission check");
            throw new VibentException(VibentError.INTERNAL_SERVER_ERROR);
        }

        Optional<Object> repositoryOpt = repositories.getRepositoryFor(targetClass);

        if (!repositoryOpt.isPresent()) {
            log.error("Couldn't find repository for entity at permission check");
            throw new VibentException(VibentError.INTERNAL_SERVER_ERROR);
        }
        Object repository = repositoryOpt.get();


        // TargetID is a numeric ID
        if (targetId instanceof Long) {
            if (!(repository instanceof CrudRepository)) {
                log.error("Entity repository isn't correct type at permission check");
                throw new VibentException(VibentError.INTERNAL_SERVER_ERROR);
            }
            targetOpt = ((CrudRepository) (repository)).findById(targetId);
        }
        // TargetID is a ref
        else if (targetId instanceof String) {
            if (!(repository instanceof CrudRefRepository)) {
                log.error("Entity repository isn't correct type at permission check");
                throw new VibentException(VibentError.INTERNAL_SERVER_ERROR);
            }
            targetOpt = ((CrudRefRepository) (repository)).findByRef((String) targetId);
        }

        /*
         * If the entity doesn't exist then we say that he has permission on it to return
         * correct business error (generally 404)
         */
        if (!targetOpt.isPresent()) {
            return true;
        }

        return this.hasPermission(authentication, targetOpt.get(), permission);
    }
}
