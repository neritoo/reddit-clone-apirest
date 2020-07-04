package com.gavilan.redditapirest.repository;

import com.gavilan.redditapirest.model.Comment;
import com.gavilan.redditapirest.model.Post;
import com.gavilan.redditapirest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);
}
