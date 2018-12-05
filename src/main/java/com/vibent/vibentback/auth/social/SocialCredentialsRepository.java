package com.vibent.vibentback.auth.social;

import com.vibent.vibentback.auth.social.provider.Provider;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface SocialCredentialsRepository extends CrudRepository<SocialCredentials, Long> {

    Optional<SocialCredentials> findByProviderAndProviderId(Provider provider, String providerId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE social_credentials SET deleted = FALSE WHERE id = :id", nativeQuery = true)
    int recover(@Param("id") Long id);

    @Query(value = "SELECT deleted FROM user WHERE id = :id", nativeQuery = true)
    boolean isDeleted(@Param("id") Long id);
}