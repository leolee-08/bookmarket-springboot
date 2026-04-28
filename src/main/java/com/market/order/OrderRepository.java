package com.market.order;

import com.market.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 내 주문 목록 조회 (최신순)
    List<Order> findByUserOrderByOrderDateDesc(User user);
}
