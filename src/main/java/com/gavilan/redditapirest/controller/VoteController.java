package com.gavilan.redditapirest.controller;

import com.gavilan.redditapirest.dto.VoteDto;
import com.gavilan.redditapirest.service.VoteService;
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
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<String> vote(@RequestBody VoteDto voteDto) {

        /*
        Map<String, Object> response = new HashMap<>();
        try {

            voteService.vote(voteDto);
        } catch (SpringRedditException e) {

            response.put("message", "Error voting post");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

         */

        return new ResponseEntity<>("Voto guardado correctamente",HttpStatus.OK);
    }
}
