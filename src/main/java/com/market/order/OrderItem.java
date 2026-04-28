package com.market.order;

import com.market.bookitem.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item_tb")
@Getter @Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private int quantity;

    // 주문 시점의 가격 저장 (나중에 가격이 바뀌어도 주문 당시 가격 보존)
    @Column(name = "price_at_order", nullable = false)
    private int priceAtOrder;

    public OrderItem(Order order, Book book, int quantity) {
        this.order = order;
        this.book = book;
        this.quantity = quantity;
        this.priceAtOrder = book.getPrice();
    }

    public int getTotalPrice() {
        return priceAtOrder * quantity;
    }
}
