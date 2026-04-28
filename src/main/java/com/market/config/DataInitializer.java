package com.market.config;

import com.market.bookitem.Book;
import com.market.bookitem.BookRepository;
import com.market.member.User;
import com.market.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 기존 Swing: BookInIt.init() - book.txt 파일에서 읽어옴
 * Spring Boot: 서버 시작 시 DB에 초기 데이터 삽입
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initBooks();
        initAdminUser();
    }

    // book.txt 데이터 → DB 삽입
    private void initBooks() {
        if (bookRepository.count() > 0) return; // 이미 데이터 있으면 스킵

        bookRepository.save(new Book("ISBN1234", "쉽게 배우는 JSP 웹 프로그래밍", 27000,
                "송미영", "단계별로 쇼핑몰을 구현하며 배우는 JSP 웹 프로그래밍", "IT전문서", "2018/10/08"));
        bookRepository.save(new Book("ISBN1235", "안드로이드 프로그래밍", 33000,
                "우재남", "실습 단계별 명쾌한 멘토링", "IT전문서", "2022/01/22"));
        bookRepository.save(new Book("ISBN1236", "스크래치", 22000,
                "고광일", "컴퓨팅 사고력을 키우는 블록 코딩", "컴퓨터입문", "2019/06/10"));
        bookRepository.save(new Book("ISBN1237", "JAVA 마스터", 30000,
                "송미영", "단계별로 프로젝트를 구현하며 배우는 자바 입문서", "컴퓨터입문", "2022/12/20"));
        bookRepository.save(new Book("ISBN1238", "IT CookBook, C 언어 for Beginner 4판", 26000,
                "우재남", "제대로 이해하며 개발하는 C 프로그래밍", "컴퓨터입문", "2021/12/03"));
        bookRepository.save(new Book("ISBN1239", "파이썬 정복", 17600,
                "김상형", "파이썬 개발에 필요한 기본 지식을 모두 담은 파이썬 입문서", "컴퓨터입문", "2018/04/13"));

        System.out.println("[DataInitializer] 도서 6권 초기 데이터 삽입 완료");
    }

    // 관리자 계정 초기 생성 (기존 Admin.java: id=Admin, pw=Admin1234)
    private void initAdminUser() {
        if (userRepository.existsByEmail("admin@bookmarket.com")) return;

        User admin = new User(
                "admin@bookmarket.com",
                passwordEncoder.encode("Admin1234"),
                "관리자",
                "010-0000-0000",
                "서울시"
        );
        admin.setRole("ROLE_ADMIN");
        userRepository.save(admin);
        System.out.println("[DataInitializer] 관리자 계정 생성 완료 (admin@bookmarket.com / Admin1234)");
    }
}
