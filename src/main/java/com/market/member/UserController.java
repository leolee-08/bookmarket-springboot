package com.market.member;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        return "member/login";
    }

    // 회원가입 페이지
    @GetMapping("/register")
    public String registerPage() {
        return "member/register";
    }

    // 고객 정보 확인
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userService.findByEmail(userDetails.getUsername()));
        return "member/profile";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String phone,
                           @RequestParam String address,
                           Model model) {
        try {
            userService.register(email, password, name, phone, address);
            return "redirect:/member/login?registered=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "member/register";
        }
    }
}
