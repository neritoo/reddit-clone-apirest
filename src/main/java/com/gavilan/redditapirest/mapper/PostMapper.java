package com.gavilan.redditapirest.mapper;

import com.gavilan.redditapirest.dto.PostRequest;
import com.gavilan.redditapirest.dto.PostResponse;
import com.gavilan.redditapirest.model.Post;
import com.gavilan.redditapirest.model.Subreddit;
import com.gavilan.redditapirest.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapToDto(Post post);

}
