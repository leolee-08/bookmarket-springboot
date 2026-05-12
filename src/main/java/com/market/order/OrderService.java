package com.market.order;

import com.market.cart.Cart;
import com.market.cart.CartService;
import com.market.member.User;
import com.market.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    // 주문 생성 (장바구니 → 주문) - 기존 CartShippingPage/CartOrderBillPage 역할
    @Transactional
    public Order createOrder(String email, String deliveryAddress) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        Cart cart = cartService.getOrCreateCart(email);

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("장바구니가 비어있습니다.");
        }

        Order order = new Order(user, cart.getTotalPrice(), deliveryAddress);
        orderRepository.save(order);

        // 장바구니 → 주문 상세로 복사
        cart.getCartItems().forEach(cartItem -> {
            int ordered = cartItem.getQuantity();
            int current = cartItem.getBook().getStock();
            if (current < ordered) {
                throw new IllegalStateException(
                        cartItem.getBook().getTitle() + " 재고가 부족합니다."
                );
            }
            cartItem.getBook().setStock(current - ordered);
            OrderItem orderItem = new OrderItem(order, cartItem.getBook(), cartItem.getQuantity());
            order.getOrderItems().add(orderItem);
        });

        // 주문 후 장바구니 비우기
        cartService.clearCart(email);

        return order;
    }

    // 내 주문 목록 조회 - 기존 CartOrderBillPage 역할
    public List<Order> getMyOrders(String email) {
        // 최신 주문 순으로 목록 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 없음"));
    }
}
