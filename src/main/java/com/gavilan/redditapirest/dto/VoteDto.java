package com.gavilan.redditapirest.dto;

import com.gavilan.redditapirest.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Eze Gavilán
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {

    private VoteType voteType;
    private Long postId;
}
