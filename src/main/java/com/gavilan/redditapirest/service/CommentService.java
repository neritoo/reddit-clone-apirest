package com.gavilan.redditapirest.service;

import com.gavilan.redditapirest.dto.CommentsDto;
import com.gavilan.redditapirest.exception.SpringRedditException;
import com.gavilan.redditapirest.mapper.CommentMapper;
import com.gavilan.redditapirest.model.Comment;
import com.gavilan.redditapirest.model.NotificationEmail;
import com.gavilan.redditapirest.model.Post;
import com.gavilan.redditapirest.model.User;
import com.gavilan.redditapirest.repository.CommentRepository;
import com.gavilan.redditapirest.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Eze Gavilán
 **/

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final MailService mailService;

    @Transactional
    public CommentsDto save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new SpringRedditException("No post found with ID: " +
                        commentsDto.getPostId().toString()));

        User currentUser = authService.getCurrentUser();

        Comment comment = commentRepository.save(commentMapper.map(commentsDto, post, currentUser));

        String message = comment.getUser().getUsername()+ " posted a comment on your post: "
                + post.getPostName();

        sendCommentNotification(message, post.getUser());

        return commentMapper.mapToDto(comment);
    }

    private void sendCommentNotification(String message, User user) {

        mailService.sendMail(new NotificationEmail(user.getUsername() + " Someone commented on your post", user.getEmail(), message));
    }

}
