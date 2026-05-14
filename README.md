# 📚 BookMarket - 도서 쇼핑몰

## 프로젝트 소개
Spring Boot 기반 도서 쇼핑몰 프로젝트입니다.

## 🚀 배포 URL
https://bookmarket-springboot-production.up.railway.app

> 💡 별도 설치 없이 위 URL로 바로 접속 가능합니다!

## ☁️ 클라우드 배포 (Railway)
본 프로젝트는 Railway 클라우드 환경에 배포되어
누구나 어디서든 접속 가능합니다.

### 배포 방식
- GitHub main 브랜치 Push 시 자동 배포
- 별도 서버 구축 없이 클라우드 환경 구성
- PC / 모바일 어디서든 접속 가능

### 배포 환경
| 항목 | 내용 |
|---|---|
| 플랫폼 | Railway Cloud |
| 배포 방식 | GitHub 연동 자동 배포 |
| 포트 | 8080 |
| DB | H2 In-Memory |
| 배포 URL | https://bookmarket-springboot-production.up.railway.app |

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

## 🚀 로컬 실행 방법
1. git clone https://github.com/leolee-08/bookmarket-springboot.git
2. src/main/resources/application.properties 설정
3. BookMarketApplication.java 우클릭 → Run
4. http://localhost:8080 접속

## H2 Console 접속
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:bookmarket_db
- username: sa
- password: (공백)