package com.market.order;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 목록 (내 주문 기록) - 기존 CartOrderBillPage 역할
    @GetMapping
    public String myOrders(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("orders", orderService.getMyOrders(userDetails.getUsername()));
        return "order/list";
    }

    // 주문 상세
    @GetMapping("/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.findById(orderId));
        return "order/detail";
    }

    // 주문하기 (배송지 입력 후 주문) - 기존 CartShippingPage 역할
    @PostMapping("/create")
    public String createOrder(@RequestParam String deliveryAddress,
                              @AuthenticationPrincipal UserDetails userDetails) {
        Order order = orderService.createOrder(userDetails.getUsername(), deliveryAddress);
        return "redirect:/order/" + order.getOrderId();
    }
}
