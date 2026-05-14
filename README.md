# 📚 BookMarket - 도서 쇼핑몰

## 프로젝트 소개
Spring Boot 기반 도서 쇼핑몰 프로젝트입니다.

## 🚀 배포 URL
https://bookmarket-springboot-production.up.railway.app

## 👥 팀원 및 역할
| 이름 | 담당 기능 |
|---|---|
| 팀장 (Lee) | 회원, Spring Security, OAuth2 |
| 팀원 A | 도서 관리 |
| 팀원 B | 장바구니 |
| 팀원 C | 주문 |
| 팀원 D | 문서, ERD |

## 🛠 기술 스택
- Backend: Spring Boot, JPA, Spring Security, OAuth2
- Frontend: Thymeleaf, jQuery
- Database: H2 Database (In-Memory)
- 배포: Railway Cloud
- 형상관리: GitHub

## 🔑 소셜 로그인
- Google OAuth2
- Naver OAuth2

## 🚀 실행 방법
1. git clone https://github.com/leolee-08/bookmarket-springboot.git
2. src/main/resources/application.properties 설정
3. BookMarketApplication.java 우클릭 → Run
4. http://localhost:8080 접속

## H2 Console 접속
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:bookmarket_db
- username: sa
- password: (공백)