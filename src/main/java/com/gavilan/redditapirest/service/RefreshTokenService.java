package com.gavilan.redditapirest.service;

import com.gavilan.redditapirest.exception.SpringRedditException;
import com.gavilan.redditapirest.model.RefreshToken;
import com.gavilan.redditapirest.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author: Eze Gavilán
 **/

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(new Date());

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token) {

        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringRedditException("Invalid refresh token"));
    }

    public void deleteRefreshToken(String token) {

        refreshTokenRepository.deleteByToken(token);
    }
}
