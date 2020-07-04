package com.gavilan.redditapirest.controller;

import com.gavilan.redditapirest.dto.SubredditDto;
import com.gavilan.redditapirest.exception.SpringRedditException;
import com.gavilan.redditapirest.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<?> createSubreddit(@RequestBody SubredditDto subredditDto) {

        Map<String, Object> response = new HashMap<>();
        SubredditDto subreddit;

        try {

            subreddit = subredditService.save(subredditDto);
        } catch (SpringRedditException e) {
            response.put("message", "Error al guardar subreddit");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("subreddit", subreddit);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllSubreddits() {

        Map<String, Object> response = new HashMap<>();
        List<SubredditDto> subreddits;

        try {

            subreddits = subredditService.getAll();
        } catch (SpringRedditException e) {
            response.put("message", "Error al obtener subreddits del servidor");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (subreddits.size() == 0) {
            response.put("error", "No existen subreddits en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("subreddits", subreddits);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubreddit(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        SubredditDto subreddit;

        try {

            subreddit = subredditService.getSubreddit(id);

        } catch (SpringRedditException e) {

            response.put("message", "Error executing query in the database");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("subreddit", subreddit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
