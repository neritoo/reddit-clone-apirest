package com.gavilan.redditapirest.controller;

import com.gavilan.redditapirest.dto.CommentsDto;
import com.gavilan.redditapirest.exception.SpringRedditException;
import com.gavilan.redditapirest.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Eze Gavilán
 **/

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentsDto> createComment(@RequestBody CommentsDto commentsDto) {

        return new ResponseEntity<>(commentService.save(commentsDto), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getCommentsForPost(@PathVariable Long postId) {

        Map<String, Object> response = new HashMap<>();
        List<CommentsDto> comments;

        try{
            comments = commentService.getAllCommentsForPost(postId);

        } catch (SpringRedditException e) {
            response.put("message", "Error retrieving the comment from the server");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comments.size() == 0) {
            response.put("error", "The post with ID: " + postId + " doesn't have any comments");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("comments", comments);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<CommentsDto>> getCommentsForUser(@PathVariable String username) {

        return new ResponseEntity<>(commentService.getAllCommentsForUser(username), HttpStatus.OK);
    }

}
