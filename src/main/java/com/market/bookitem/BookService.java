package com.market.bookitem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("도서를 찾을 수 없습니다."));
    }

    public List<Book> search(String keyword)  { // 제목 키워드로 도서 검색
        return bookRepository.findByTitleContaining(keyword);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
