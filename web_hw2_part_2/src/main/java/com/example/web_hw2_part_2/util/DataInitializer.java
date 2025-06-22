package com.example.web_hw2_part_2.util;

import com.example.web_hw2_part_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.web_hw2_part_2.entity.UserEntity;

@Component
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findByUsername("user1").isEmpty()) {
            UserEntity user = new UserEntity();
            user.setUsername("user1");
            user.setPassword(passwordEncoder.encode("password1"));
            userRepository.save(user);
        }
        if (userRepository.findByUsername("user2").isEmpty()) {
            UserEntity user = new UserEntity();
            user.setUsername("user2");
            user.setPassword(passwordEncoder.encode("password2"));
            userRepository.save(user);
        }
        if (userRepository.findByUsername("user3").isEmpty()) {
            UserEntity user = new UserEntity();
            user.setUsername("user3");
            user.setPassword(passwordEncoder.encode("password3"));
            userRepository.save(user);
        }
        System.out.println("Application started - running DataInitializer");
    }
}
