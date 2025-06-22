package com.example.web_hw2_part_2.controller;

import com.example.web_hw2_part_2.entity.UserEntity;
import com.example.web_hw2_part_2.entity.UserSessionEntity;
import com.example.web_hw2_part_2.repository.UserRepository;
import com.example.web_hw2_part_2.repository.UserSessionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository sessionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<JsonNode> getSession(Authentication authentication) {
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username).orElseThrow();

        UserSessionEntity sessionEntity = sessionRepository.findByUser(user).orElse(null);
        ObjectNode responseJson = objectMapper.createObjectNode();

        if (sessionEntity == null) {
            // Return empty session object if none exists.
            responseJson.put("title", "");
            responseJson.putArray("shapes");
        } else {
            responseJson.put("title", sessionEntity.getTitle() != null ? sessionEntity.getTitle() : "");
            try {
                JsonNode shapesNode = objectMapper.readTree(sessionEntity.getShapesJson());
                responseJson.set("shapes", shapesNode);
            } catch (JsonProcessingException e) {
                responseJson.putArray("shapes"); // fallback empty array
            }
        }
        return ResponseEntity.ok(responseJson);
    }

    @PostMapping
    public ResponseEntity<Void> saveSession(Authentication authentication, @RequestBody JsonNode sessionJson) {
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username).orElseThrow();

        String title = sessionJson.has("title") ? sessionJson.get("title").asText() : "";
        JsonNode shapesNode = sessionJson.has("shapes") ? sessionJson.get("shapes") : objectMapper.createArrayNode();

        try {
            String shapesJsonString = objectMapper.writeValueAsString(shapesNode);

            UserSessionEntity sessionEntity = sessionRepository.findByUser(user)
                    .orElse(new UserSessionEntity());

            sessionEntity.setUser(user);
            sessionEntity.setTitle(title);
            sessionEntity.setShapesJson(shapesJsonString);

            sessionRepository.save(sessionEntity);
            return ResponseEntity.ok().build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
