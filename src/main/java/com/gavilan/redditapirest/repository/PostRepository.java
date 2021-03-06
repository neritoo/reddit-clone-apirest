package com.gavilan.redditapirest.repository;

import com.gavilan.redditapirest.model.Post;
import com.gavilan.redditapirest.model.Subreddit;
import com.gavilan.redditapirest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);

    List<Post> findAllByOrderByCreatedDateDesc();
}
