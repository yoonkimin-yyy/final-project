|![Image](https://github.com/user-attachments/assets/ec576154-88fb-49fc-9c65-ca4b120c6e14)|
|:-----------------------------:|
|아래는 구현한 사이트입니다.|
|<http://bbanggil.kro.kr>|

### (디지털 컨버전스_혼합) 데이터 융합 자바 & 스프링 (스프링 부트) 웹 개발자 양성과정에서
### 파이널 프로젝트입니다.
### 프로젝트 기간은 02/18 ~ 04/02 입니다.

## 설명

빵집 리스트를 모아 둔 사이트입니다.

최신, 인기, 메뉴 카테고리, 지역별로 빵집 리스트를 볼 수 있습니다.

빵집 페이지에서 메뉴,리뷰,가게 사진 등을 볼 수 있고 주문이 가능합니다.

사장님 페이지에서는 주문 관리, 매출액을 볼 수 있습니다.

관리자 페이지에서는 유저, 빵집 관리 등 할 수 있습니다.

## 🛠 기술 스택

### 💻 Back-end
- **Java 17**, **Spring MVC**, **Oracle**, **MyBatis**, **Thymeleaf**

### 🎨 Front-end
- **HTML**, **CSS**, **JavaScript**

### ⚙️ DevOps & Tools
- **Git**, **GitHub**, **STS4**

### 🌐 External API
- **Kakao Map API**, **PortOne Payment API**, **사업자 API**, **문자 API**, **주소 API**

## 📂 폴더 구조

```
📦java
 ┗ 📂kr
 ┃ ┗ 📂kro
 ┃ ┃ ┗ 📂bbanggil
 ┃ ┃ ┃ ┣ 📂admin
 ┃ ┃ ┃ ┃ ┣ 📂api
 ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┃ ┣ 📂interceptor
 ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┣ 📂common
 ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┃ ┣ 📂response
 ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┣ 📂scheduler
 ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┗ 📂util
 ┃ ┃ ┃ ┣ 📂global
 ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┣ 📂filter
 ┃ ┃ ┃ ┃ ┗ 📂handler
 ┃ ┃ ┃ ┣ 📂newsletter
 ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┣ 📂owner
 ┃ ┃ ┃ ┃ ┣ 📂order
 ┃ ┃ ┃ ┃ ┃ ┣ 📂api
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┗ 📂pickup
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┣ 📂response
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┣ 📂user
 ┃ ┃ ┃ ┃ ┣ 📂bakery
 ┃ ┃ ┃ ┃ ┃ ┣ 📂api
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂response
 ┃ ┃ ┃ ┃ ┃ ┣ 📂interceptor
 ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┣ 📂scheduler
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┣ 📂util
 ┃ ┃ ┃ ┃ ┃ ┗ 📂vo
 ┃ ┃ ┃ ┃ ┗ 📂member
 ┃ ┃ ┃ ┃ ┃ ┣ 📂api
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┗ 📂util
```

## 조원/맡은 기능

### 이재형
- AWS EC2 Oracle 서버 구축
- S3 연동
- 마이페이지 내 정보
- 빵집 등록/수정, 메뉴 등록 수정/삭제
- 관리자 신고 확인/유저밴
- 관리자 트래픽 확인
- 패키지 구조 개선/정리

### 한봉오
- AWS EC2 Apache+Tomcat 서버 구축 및 DB연동
- SFTP 수동 배포
- 결제(사전/사후검증) 포트원 API
- 관리자 빵집 등록 관리, 사용자 관리(사용자 정보)
- 관리자 로그인/계정관리 페이지

### 윤기민
- 메인 페이지(카카오 맵, 이메일 전송)
- 빵집 페이지(카카오 맵 API, 빵집 정보)
- 리뷰등록 등록/수정/삭제 (별점, 태그, S3)
- 장바구니
- 관리자 문의 등록/조회
- 결제 내역

### 전주영
- 일반/사업자 회원가입(사업자API, 문자API, 주소API)
- 로그인
- 아이디/비밀번호 찾기

### 김원민
- 빵집 리스트 페이지(카카오 맵API)
- 마이페이지(구매 내역, 비밀번호/주소 변경, 리뷰등록,삭제)

### 김민우
- 사장님 페이지(예약 관리)
- 연간 매출 확인(날짜별)
- 사장님 리뷰 답글
- 리뷰 신고






