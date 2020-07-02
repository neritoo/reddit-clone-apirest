package com.gavilan.redditapirest.service;

import com.gavilan.redditapirest.dto.SubredditDto;
import com.gavilan.redditapirest.exception.SpringRedditException;
import com.gavilan.redditapirest.model.Subreddit;
import com.gavilan.redditapirest.model.User;
import com.gavilan.redditapirest.repository.SubredditRepository;
import com.gavilan.redditapirest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Eze Gavilan
**/

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {

        Subreddit save = subredditRepository.save(mapSubredditDto(subredditDto));
        subredditDto.setId(save.getId());
        subredditDto.setUsername(save.getUser().getUsername());
        // Al crear un nuevo Subreddit el número de posts = 0.
        // No se puede save.getPosts().size() -> No se inicializa el array list en model.Subreddit: Una solución
        // sería en el constructor de Subreddit -> this.posts = new ArrayList();
        subredditDto.setNumberOfPosts(0);

        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID: " + id));

        return mapToDto(subreddit);
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder().name(subreddit.getName())
                .id(subreddit.getId())
                .username(subreddit.getUser().getUsername())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }

    private User getUserLogged() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDetails userDetails = null;

        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }

        assert userDetails != null;
        String username = userDetails.getUsername();

        return userRepository.findByUsername(username).orElse(null);

    }

    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
        User userLogged = getUserLogged();

        return Subreddit.builder().name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .user(userLogged)
                .build();
    }


}
