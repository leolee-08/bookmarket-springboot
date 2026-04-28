package com.market.config;

import com.market.bookitem.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BookService bookService;

    @GetMapping
    public String adminMain(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "admin/index";
    }
}