package com.gavilan.redditapirest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubredditDto {

    private Long id;
    private String name;
    private String description;
    private String username;
    private Integer numberOfPosts;
}
