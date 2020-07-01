package com.gavilan.redditapirest.controller;

import com.gavilan.redditapirest.dto.SubredditDto;
import com.gavilan.redditapirest.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubrreditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
        return new ResponseEntity<>(subredditService.save(subredditDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return new ResponseEntity<>(subredditService.getAll(), HttpStatus.OK);
    }

}
