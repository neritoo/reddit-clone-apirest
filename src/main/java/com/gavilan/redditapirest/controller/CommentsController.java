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

        /*
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

         */

        return new ResponseEntity<>(commentService.save(commentsDto), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentsDto>> getCommentsForPost(@PathVariable Long postId) {

        /*
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

         */

        return new ResponseEntity<>(commentService.getAllCommentsForPost(postId), HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<CommentsDto>> getCommentsForUser(@PathVariable String username) {

        /*
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

         */

        return new ResponseEntity<>(commentService.getAllCommentsForUser(username), HttpStatus.OK);
    }

}
