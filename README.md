# 📚 BookMarket - 도서 쇼핑몰

## 프로젝트 소개
Spring Boot 기반 도서 쇼핑몰 프로젝트입니다.

## 팀원 및 역할
| 이름 | 담당 기능 |
|---|---|
| 팀장 | 회원, 로그인/Spring Security |
| 팀원A | 도서 관리 |
| 팀원B | 장바구니 |
| 팀원C | 주문 |
| 팀원D | 문서, ERD |

## 기술 스택
- Backend: Spring Boot, JPA, Spring Security
- Frontend: Thymeleaf, jQuery
- Database: H2 Database (In-Memory)
- 형상관리: GitHub

## 실행 방법
1. git clone https://github.com/[팀장ID]/bookmarket-springboot.git
2. src/main/resources/application.properties 설정
3. BookMarketApplication.java 우클릭 → Run
4. http://localhost:8080 접속

## H2 Console 접속
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:bookmarket_db
- username: sa
- password: (공백)