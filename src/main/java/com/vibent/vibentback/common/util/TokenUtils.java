package com.vibent.vibentback.common.util;

import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;

@Component
public class TokenUtils {

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Value("${vibent.auth.secret}")
    private String SECRET;

    @Value("${vibent.auth.issuer}")
    private String ISSUER;

    @Value("${vibent.auth.expirationSeconds}")
    private long AUTH_EXPIRATION_SECONDS;

    @Value("${vibent.group.inviteTokenExpirationSeconds}")
    private long INVITE_GROUP_EXPIRATION_SECONDS;

    private String createToken(String subject, long expireSeconds) {
        String id = UUID.randomUUID().toString();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
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

    public String createUserAuthenticationToken(String userRef) {
        return createToken(userRef, AUTH_EXPIRATION_SECONDS);
    }

    public String createGroupInviteToken(String groupRef) {
        return createToken(groupRef, INVITE_GROUP_EXPIRATION_SECONDS);
    }

    public Claims validateJWTToken(String jwt) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                    .parseClaimsJws(jwt).getBody();
            if(!claims.getIssuer().equals(ISSUER))
                throw new VibentException(VibentError.ILLEGAL_TOKEN_ISSUER);
        } catch (ExpiredJwtException e) {
            throw new VibentException(VibentError.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e ){
            throw new VibentException(VibentError.UNSUPPORTED_TOKEN);
        } catch (MalformedJwtException e){
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        } catch (SignatureException e) {
            throw new VibentException(VibentError.INVALID_TOKEN_SIGNATURE);
        } catch (IllegalArgumentException e) {
            throw new VibentException(VibentError.ILLEGAL_TOKEN_ARGUMENT);
        }

        return claims;
    }
}
