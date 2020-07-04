package com.gavilan.redditapirest.mapper;

import com.gavilan.redditapirest.dto.CommentsDto;
import com.gavilan.redditapirest.model.Comment;
import com.gavilan.redditapirest.model.Post;
import com.gavilan.redditapirest.model.User;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "createdDate", expression = "java(new java.util.Date())")
    @Mapping(target = "user", source = "user")
    Comment map(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    @Mapping(target = "duration", expression = "java(getDuration(comment))")
    CommentsDto mapToDto(Comment comment);

    default String getDuration(Comment comment) {
        return TimeAgo.using(comment.getCreatedDate().getTime());
    }

}
