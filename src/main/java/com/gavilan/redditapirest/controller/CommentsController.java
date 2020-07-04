package com.gavilan.redditapirest.controller;

import com.gavilan.redditapirest.dto.CommentsDto;
import com.gavilan.redditapirest.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<CommentsDto>> getCommentsForPost(@PathVariable Long postId) {

        return new ResponseEntity<>(commentService.getAllCommentsForPost(postId), HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<CommentsDto>> getCommentsForUser(@PathVariable String username) {

        return new ResponseEntity<>(commentService.getAllCommentsForUser(username), HttpStatus.OK);
    }

}
