package com.market.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 기존 Swing: Person(name, phone, address) + User 상속 구조
 * Spring Boot: 단일 Entity로 통합 + 로그인(email/password) 추가
 */
@Entity
@Table(name = "user_tb")
@Getter @Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String address;

    // ROLE_USER, ROLE_ADMIN
    @Column(nullable = false, length = 20)
    private String role = "ROLE_USER";

    // 소셜 로그인용 (google, naver, null=자체)
    @Column(length = 20)
    private String provider;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public User(String email, String password, String name, String phone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
