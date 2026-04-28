package com.market.bookitem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        List<Book> books = (keyword != null && !keyword.isBlank())
                ? bookService.search(keyword)
                : bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        return "book/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "book/detail";
    }

    // ===== 관리자 기능 =====
    @GetMapping("/admin")
    public String adminMain(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/new")
    public String newBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "admin/book-form";
    }

    @GetMapping("/admin/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "admin/book-form";
    }

    @PostMapping("/admin/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/books/admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books/admin";
    }
}