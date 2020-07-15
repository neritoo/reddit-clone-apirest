package com.gavilan.redditapirest.controller;

import com.gavilan.redditapirest.model.User;
import com.gavilan.redditapirest.repository.UserRepository;
import com.gavilan.redditapirest.service.UploadImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Eze Gavilán
 **/

@RestController
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;
    private final UploadImageService uploadImageService;

    @PostMapping("/storage/upload")
    public ResponseEntity<?> uploadPhoto(@RequestParam MultipartFile file, @RequestParam String username) {

        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findByUsername(username).orElse(null);

        if (!file.isEmpty()) {
            String fileName = null;

            try {
                fileName = uploadImageService.subir(file);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir imágen");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String prevPhoto = user.getPhoto();
            uploadImageService.eliminar(prevPhoto);

            user.setPhoto(fileName);
            userRepository.save(user);
            response.put("message", "Imágen subida con éxito");
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
