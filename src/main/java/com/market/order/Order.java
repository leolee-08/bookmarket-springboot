package com.market.order;

import com.market.member.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_tb")
@Getter @Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    // 로그인한 사용자와 연결 → 내 주문만 조회 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(length = 50)
    private String status = "주문완료";

    @Column(name = "delivery_address", length = 255)
    private String deliveryAddress;

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(User user, int totalPrice, String deliveryAddress) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
    }
}
