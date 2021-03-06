package com.gavilan.redditapirest.controller;

import com.gavilan.redditapirest.dto.PostRequest;
import com.gavilan.redditapirest.dto.PostResponse;
import com.gavilan.redditapirest.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Eze Gavilán
 **/

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {

        /*
        Map<String, Object> response = new HashMap<>();
        PostResponse postResponse;

        try {

            postResponse = postService.save(postRequest);
        } catch (SpringRedditException e) {
            response.put("message", "Error al guardar post");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("post", postResponse);

         */

        return new ResponseEntity<>(postService.save(postRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {

        /*
        Map<String, Object> response = new HashMap<>();
        List<PostResponse> posts;

        try {

            posts = postService.getAllPosts();
        } catch (SpringRedditException e) {
            response.put("message", "Error al obtener posts del servidor");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (posts.size() == 0) {
            response.put("error", "No existen posts en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("posts", posts);

         */

        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {

        /*
        Map<String, Object> response = new HashMap<>();
        PostResponse postResponse;

        try {

            postResponse = postService.getPost(id);
        } catch (SpringRedditException e) {
            response.put("message", "Error al obtener Post");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("post", postResponse);

         */

        return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
    }

    @GetMapping("subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id) {

        /*
        Map<String, Object> response = new HashMap<>();
        List<PostResponse> posts;

        try {

            posts = postService.getPostsBySubreddit(id);
        } catch (SpringRedditException e) {
            response.put("message", "Error al obtener posts del subreddit.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (posts.size() == 0) {
            response.put("error", "No se encontraron posts del subreddit con ID: " + id.toString());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("posts", posts);

         */

        return new ResponseEntity<>(postService.getPostsBySubreddit(id), HttpStatus.OK);
    }

    @GetMapping("username/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name) {

        /*
        Map<String, Object> response = new HashMap<>();
        List<PostResponse> posts;

        try {

            posts = postService.getPostsByUsername(name);
        } catch (SpringRedditException e) {
            response.put("message", "Error al obtener posts del usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("posts", posts);

         */

        return new ResponseEntity<>(postService.getPostsByUsername(name), HttpStatus.OK);
    }
}
