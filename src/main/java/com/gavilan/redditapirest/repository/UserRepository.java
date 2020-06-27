package com.gavilan.redditapirest.repository;

import com.gavilan.redditapirest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
