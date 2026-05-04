package com.market.cart;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 장바구니 목록 - 기존 CartItemListPage 역할
    @GetMapping
    public String cartList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Cart cart = cartService.getOrCreateCart(userDetails.getUsername());
        model.addAttribute("cart", cart);
        return "cart/list";
    }

    // 도서 추가
    @PostMapping("/add/{bookId}")
    public String addBook(@PathVariable Long bookId,
                          @RequestParam(defaultValue = "1") int quantity,
                          @AuthenticationPrincipal UserDetails userDetails) {
        cartService.addBook(userDetails.getUsername(), bookId, quantity);
        return "redirect:/cart";
    }

    // 수량 변경
    @PostMapping("/update/{cartItemId}")
    public String updateQuantity(@PathVariable Long cartItemId,
                                 @RequestParam int quantity) {
        cartService.updateQuantity(cartItemId, quantity);
        return "redirect:/cart";
    }

    // 항목 삭제
    @PostMapping("/remove/{cartItemId}")
    public String removeItem(@PathVariable Long cartItemId) {
        cartService.removeItem(cartItemId);
        return "redirect:/cart";
    }

    // 전체 비우기
    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        cartService.clearCart(userDetails.getUsername());
        return "redirect:/cart";
    }
}
