package com.vibent.vibentback.common.util;

import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.user.ConnectedUserUtils;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * Utils for generating and reading JWT tokens, used for authentication
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JWTUtils {

    private final ConnectedUserUtils connectedUserUtils;

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Value("${vibent.auth.secret}")
    private String SECRET;

    @Value("${vibent.auth.issuer}")
    private String ISSUER;

    @Value("${vibent.auth.expirationSecs}")
    private long USER_AUTH_EXPIRATION_SECS;

    private static final String USER_AUTH_ID = "user_auth";

    private static final String EVENT_NOTIFEED_ID = "notifeed";
    private static final long EVENT_NOTIFEED_EXPIRATION_SECS = 300;

    public String createUserAuthToken(String userRef) {
        return createToken(userRef, USER_AUTH_EXPIRATION_SECS, USER_AUTH_ID);
    }

    public Claims validateUserAuthToken(String jwt) {
        return validateToken(jwt, USER_AUTH_ID);
    }

    public String createEventNotifeedToken(Long eventId) {
        String subject = String.format("%d %s", eventId, connectedUserUtils.getConnectedUserRef());
        return createToken(subject, EVENT_NOTIFEED_EXPIRATION_SECS, EVENT_NOTIFEED_ID);
    }

    private String createToken(String subject, long expireSeconds, String id) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our secret
        byte[] apiKeySecretBytes = Base64.getEncoder().encode(SECRET.getBytes(StandardCharsets.UTF_8));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SIGNATURE_ALGORITHM.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(ISSUER)
                .setExpiration(new Date(nowMillis + (expireSeconds * 1000)))
                .signWith(SIGNATURE_ALGORITHM, signingKey);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    private Claims validateToken(String jwt, String expectedId) {
        Claims claims = validateToken(jwt);
        if (!expectedId.equals(claims.getId())) {
            throw new VibentException(VibentError.WRONG_TOKEN_TYPE);
        }
        return claims;
    }

    private Claims validateToken(String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            throw new VibentException(VibentError.NO_TOKEN);
        }
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encode(SECRET.getBytes(StandardCharsets.UTF_8)))
                    .parseClaimsJws(jwt).getBody();
            if (!claims.getIssuer().equals(ISSUER))
                throw new VibentException(VibentError.ILLEGAL_TOKEN_ISSUER);
        } catch (ExpiredJwtException e) {
            throw new VibentException(VibentError.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new VibentException(VibentError.UNSUPPORTED_TOKEN);
        } catch (MalformedJwtException e) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        } catch (SignatureException e) {
            throw new VibentException(VibentError.INVALID_TOKEN_SIGNATURE);
        } catch (IllegalArgumentException e) {
            throw new VibentException(VibentError.ILLEGAL_TOKEN_ARGUMENT);
        }

        return claims;
    }
}
