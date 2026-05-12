package com.market.cart;

import com.market.bookitem.Book;
import com.market.bookitem.BookRepository;
import com.market.member.User;
import com.market.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // 내 장바구니 조회 (없으면 생성)
    @Transactional
    public Cart getOrCreateCart(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        return cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(new Cart(user)));
    }

    // 장바구니에 도서 추가
    @Transactional
    public void addBook(String email, Long bookId, int quantity) {
        Cart cart = getOrCreateCart(email);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("도서 없음"));

        if (book.getStock() <= 0) {
            throw new IllegalStateException("재고가 없습니다.");
        }

        cart.getCartItems().stream()
                .filter(item -> item.getBook().getBookId().equals(bookId))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            if (item.getQuantity() + quantity > book.getStock()) {
                                throw new IllegalStateException("재고 수량을 초과했습니다.");
                            }
                            item.setQuantity(item.getQuantity() + quantity);
                        },
                        () -> {
                            CartItem newItem = new CartItem(cart, book);
                            newItem.setQuantity(quantity); // ← 신규 담기 시 수량 반영
                            cartItemRepository.save(newItem);
                        }
                );
    }

    // 수량 변경 - 기존 Cart.updateQuantity() 동일 로직
    @Transactional
    public void updateQuantity(Long cartItemId, int quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목 없음"));
        if (quantity <= 0) {
            cartItemRepository.delete(item);
        } else {
            // 재고 초과 체크 추가
            if (quantity > item.getBook().getStock()) {
                throw new IllegalStateException("재고 수량을 초과했습니다.");
            }
            item.setQuantity(quantity);
        }
    }

    // 항목 삭제 - 기존 Cart.removeCart() 동일 로직
    @Transactional
    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // 전체 비우기 - 기존 Cart.deleteBook() 동일 로직
    @Transactional
    public void clearCart(String email) {
        Cart cart = getOrCreateCart(email);
        cart.getCartItems().clear();


    }
}
