package com.gavilan.redditapirest.repository;

import com.gavilan.redditapirest.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
}
