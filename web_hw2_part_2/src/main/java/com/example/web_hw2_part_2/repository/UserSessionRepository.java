package com.example.web_hw2_part_2.repository;

import com.example.web_hw2_part_2.entity.UserEntity;
import com.example.web_hw2_part_2.entity.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Long> {
    Optional<UserSessionEntity> findByUser(UserEntity user);
}

