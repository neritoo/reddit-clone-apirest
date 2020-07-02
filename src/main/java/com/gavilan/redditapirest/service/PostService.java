package com.gavilan.redditapirest.service;

import com.gavilan.redditapirest.dto.PostRequest;
import com.gavilan.redditapirest.dto.PostResponse;
import com.gavilan.redditapirest.exception.SpringRedditException;
import com.gavilan.redditapirest.mapper.PostMapper;
import com.gavilan.redditapirest.model.Post;
import com.gavilan.redditapirest.model.Subreddit;
import com.gavilan.redditapirest.model.User;
import com.gavilan.redditapirest.repository.PostRepository;
import com.gavilan.redditapirest.repository.SubredditRepository;
import com.gavilan.redditapirest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Eze Gavilán
 **/

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    @Transactional
    public PostResponse save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SpringRedditException("No subreddit found with name: " +
                        postRequest.getSubredditName()));

        User currentUser = authService.getCurrentUser();

        Post post = postRepository.save(postMapper.map(postRequest, subreddit, currentUser));

        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("Post not found with ID: " +
                        id.toString()));

        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {

        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID: " +
                        subredditId.toString()));

        List<Post> posts = postRepository.findAllBySubreddit(subreddit);

        return posts
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringRedditException("No user found with name: " +
                        username));

        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
