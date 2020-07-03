package com.gavilan.redditapirest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: Eze Gavilán
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsDto {

    private Long id;
    private Long postId;
    private Date createdDate;
    private String text;
    private String username;
}
