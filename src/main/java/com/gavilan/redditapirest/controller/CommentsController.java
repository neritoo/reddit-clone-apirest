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
    public ResponseEntity<?> createComment(@RequestBody CommentsDto commentsDto) {

        Map<String, Object> response = new HashMap<>();
        CommentsDto comment;

        try {

            comment = commentService.save(commentsDto);
        } catch (SpringRedditException e) {

            response.put("message","Error al guardar comentario");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("comment", comment);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
            response.put("error", "El post con Id: " + postId + " no tiene ningún comentario.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("comments", comments);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getCommentsForUser(@PathVariable String username) {

        Map<String, Object> response = new HashMap<>();
        List<CommentsDto> comments;

        try {

            comments = commentService.getAllCommentsForUser(username);
        } catch (SpringRedditException e) {
            response.put("message", "Error al obtener los comentarios del usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comments.size() == 0) {
            response.put("error", "El usuario " + username + " no tiene ningún comentario." );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("comments", comments);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
