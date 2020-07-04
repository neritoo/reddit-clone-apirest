package com.gavilan.redditapirest.model;

import com.gavilan.redditapirest.exception.SpringRedditException;

import java.util.Arrays;

public enum VoteType {


    UPVOTE(1), DOWNVOTE(-1),
    ;

    private Integer direction;

    VoteType(int direction) {

    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new SpringRedditException("Vote not found"));
    }

    private Integer getDirection() {
        return direction;
    }
}
