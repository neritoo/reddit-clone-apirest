package com.gavilan.redditapirest.service;

import com.gavilan.redditapirest.dto.VoteDto;
import com.gavilan.redditapirest.exception.SpringRedditException;
import com.gavilan.redditapirest.model.Post;
import com.gavilan.redditapirest.model.User;
import com.gavilan.redditapirest.model.Vote;
import com.gavilan.redditapirest.repository.PostRepository;
import com.gavilan.redditapirest.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.gavilan.redditapirest.model.VoteType.UPVOTE;

/**
 * @author: Eze Gavilán
 **/

@Service
@AllArgsConstructor
@Slf4j
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    public void vote(VoteDto voteDto) {

        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new SpringRedditException("No post found with ID: " +
                        voteDto.getPostId()));

        User currentUser = authService.getCurrentUser();

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, currentUser);

        // Validación: Valida que el mismo usuario no pueda votar de nuevo en el mismo post.
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }

        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapToVote(voteDto, post, currentUser));
        postRepository.save(post);

    }

    private Vote mapToVote(VoteDto voteDto, Post post, User user) {

        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(user)
                .build();
    }
}
