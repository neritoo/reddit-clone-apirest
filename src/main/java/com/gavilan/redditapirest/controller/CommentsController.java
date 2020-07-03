package com.gavilan.redditapirest.controller;

import com.gavilan.redditapirest.dto.CommentsDto;
import com.gavilan.redditapirest.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
