# item-service
### spring boot + thymeleaf
<hr>

## 요구사항
> 상품을 관리할 수 있는 서비스.
 
- 상품 도메인 모델
  - 상품 ID
  - 상품명
  - 가격
  - 수량

- 상품 관리 기능
  - 상품 목록
  - 상품 상세
  - 상품 등록
  - 상품 수정

<details>
<summary style="font-size: 18px;"> 추가 요구사항 </summary>
<div markdown="1">

> 타임리프를 사용해서 폼에서 체크박스, 라디오 버튼, 셀렉트 박스를 편리하게 사용하는 방법
> 
> 기존 서비스에서 다음 요구사항을 추가

- 판매 여부
  - 판매 오픈 여부
  - 체크 박스로 선택할 수 있다.
  
- 등록 지역
  - 서울, 부산, 제주
  - 체크 박스로 다중 선택할 수 있다.
  
- 상품 종류
  - 도서, 식품, 기타
  - 라디오 버튼으로 하나만 선택할 수 있다.
  
- 배송 방식
  - 빠른 배송
  - 일반 배송
  - 느린 배송
  - 셀렉트 박스로 하나만 선택할 수 있다.

- 국제화
  - 메시지를 한 곳에서 관리하도록 하는 기능을 메시지 기능 구현.
  - en 국제화 진행.
</div>
</details>

<br>

<details>
<summary style="font-size: 18px;"> 검증 요구사항 </summary>
<div markdown="1">

- 타입 검증
  - 가격, 수량에 문자가 들어가면 검증 오류 처리
  
- 필드 검증
  - 상품명: 필수, 공백X
  - 가격: 1000원 이상, 1백만원 이하
  - 수량: 최대 9999
  
- 특정 필드의 범위를 넘어서는 검증
  - 가격 * 수량의 합은 10,000원 이상
  
- 수정시
  - 수량 100000개로 제한해제

</div>
</details>

<br>

<details>
<summary style="font-size: 18px;"> 로그인 요구사항 </summary>
<div markdown="1">

- 홈 화면 -로그인 전
  - 회원가입
  - 로그인

- 홈 화면 -로그인 후
  - 본인 이름({Name}님 환영합니다.)
  - 상품 관리
  - 로그아웃

- 보안 요구사항
  - 로그인 사용자만 상품에 접근하고, 관리할 수 있음
  - <b>로그인 하지 않은 사용자가 상품 관리에 접근하면 로그인 화면으로 이동</b>

- 회원가입, 상품관리

</div>
</details>
