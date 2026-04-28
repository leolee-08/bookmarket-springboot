package com.market.bookitem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 기존 Swing: Item(bookId, name, unitPrice) + Book 상속 구조
 * Spring Boot: 단일 Entity로 통합, book.txt 데이터 → DB로 전환
 */
@Entity
@Table(name = "book_tb")
@Getter @Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    // 기존 ISBN 코드 (ISBN1234 등)
    @Column(name = "isbn", unique = true, length = 20)
    private String isbn;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false)
    private int price;

    @Column(length = 255)
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String category;

    @Column(name = "release_date", length = 20)
    private String releaseDate;

    @Column(name = "cover_image_url", length = 255)
    private String coverImageUrl;

    // 재고
    @Column(nullable = false)
    private int stock = 100;

    public Book(String isbn, String title, int price, String author,
                String description, String category, String releaseDate) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.author = author;
        this.description = description;
        this.category = category;
        this.releaseDate = releaseDate;
        this.coverImageUrl = "/images/" + isbn + ".jpg";
    }
}
