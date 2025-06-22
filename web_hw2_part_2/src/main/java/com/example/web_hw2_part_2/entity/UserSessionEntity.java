package com.example.web_hw2_part_2.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user_sessions")
public class UserSessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String shapesJson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShapesJson() {
        return shapesJson;
    }

    public void setShapesJson(String shapesJson) {
        this.shapesJson = shapesJson;
    }
}