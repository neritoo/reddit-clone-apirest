package com.gavilan.redditapirest.repository;

import com.gavilan.redditapirest.model.Post;
import com.gavilan.redditapirest.model.User;
import com.gavilan.redditapirest.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User user);
}
