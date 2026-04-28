package com.market.cart;

import com.market.bookitem.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_item_tb")
@Getter @Setter
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private int quantity = 1;

    public CartItem(Cart cart, Book book) {
        this.cart = cart;
        this.book = book;
        this.quantity = 1;
    }

    // 기존 Swing CartItem의 getTotalPrice() 동일 로직
    public int getTotalPrice() {
        return book.getPrice() * quantity;
    }
}
