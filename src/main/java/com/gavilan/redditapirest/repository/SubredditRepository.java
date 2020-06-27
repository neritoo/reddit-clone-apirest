package com.gavilan.redditapirest.repository;

import com.gavilan.redditapirest.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
}
