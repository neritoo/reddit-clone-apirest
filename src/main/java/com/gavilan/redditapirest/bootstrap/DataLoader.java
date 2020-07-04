package com.gavilan.redditapirest.bootstrap;

import com.gavilan.redditapirest.model.User;
import com.gavilan.redditapirest.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: Eze Gavilán
 **/

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Creación de usuarios por defecto al levantar servidor.

        User neritoo = User.builder()
                .username("neritoo")
                .password(passwordEncoder.encode("1677613325"))
                .email("ezegavilan95@gmail.com")
                .created(new Date())
                .enabled(true)
                .build();

        userRepository.save(neritoo);

        User roparis = User.builder()
                .username("roparis")
                .password(passwordEncoder.encode("martinpelusacoby"))
                .email("rocioparis1097@gmail.com")
                .created(new Date())
                .enabled(true)
                .build();

        userRepository.save(roparis);
    }
}
