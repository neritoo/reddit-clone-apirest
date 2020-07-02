package com.gavilan.redditapirest.mapper;

import com.gavilan.redditapirest.dto.SubredditDto;
import com.gavilan.redditapirest.model.Post;
import com.gavilan.redditapirest.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Sirve para el mapping de Dto -> Subreddit y viceversa.
 * En mi implementación no se puede mapear así, ya que obtenemos el User
 * a partir del contexto del Security y consultamos al repository para obtener
 * el objeto User.
 */
@Deprecated
@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }


    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);

}
