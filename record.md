#### 10.11 21:40 석근
Item create 시 이미지 파일도 첨부할 수 있도록 함.  
update - 개발 도중 특이점 발견하여 롤백. (사진만 업데이트하는 팝업을 만들어야 함)   
수정한 파일 : Item 도메인, 컨트롤러, + 등록 html  + .yml(최대10MB까지만 올릴수있도록 제한)

#### 10.14 3:50 혁상
- HomeController, LoginController 수정, loginHome.html 추가 :로그인 완료 시 쿠키를 통해 로그인 완료를 알 수 있는 페이지로 이동, 로그아웃 기능 완료<br>
** 쿠키값에 회원 id가 그대로 노출되니 세션 id를 생성해서 세션저장소에 저장하여 보안상 문제를 해결해야 함.
- MemberRepository의 findByOne 메소드 이름 변경 -> findById (메소드 명이 기능을 명확하게 나타내는 것 같지 않아서..)

#### 10.16 3:40 혁상
- 로그인 시 세션에 필요한 기능직접 만듬 (생성, 조회, 삭제)
- SessionManager 추가, HomeController 수정

#### 10.16 18:20 혁상
- 직접 만든 세션 말고 http에서 제공해주는 세션을 이용해서 로그인 작동 구현
- 30분동안 반응이 없으면 로그인이 풀림
- SessionController (세션 확인하기 위해 - 지워도 상관없음), 
  SessionConst 생성
- HomeController, LoginController, application.yml 수정

#### 10.22 01:00 석근
Mypage -  내 정보 수정하기 + 내 주문 목록 보기 구현   
MypageController 생성  및 template/mypage dir 생성 
내 주문 목록 보기 구현을 위해 OrderRepo, OrderService 에 메소드 추가  
내 정보 수정하기 구현을 위해 UpdateForm+ MemberService에 변경감지수정 메소드 추가 ( 후에 new 회원 가입 form에 맞춰서 수정 필요)  
OrderRepo, OrderService, MemberService, loginHome.html 수정, 나머지파일들은 모두 이번에 새로 생성됨.  

#### 10.23 14:30 석근
item/new 접근 시 판매자만 들어갈 수 있도록 구현  
errorMessage를 alert로 띄우고 redirect를 설정하여 보내게 설정 (다른곳에도활용가능)
templates/error/errorMessage.html / ItemController.java
OrderRepo, OrderService, MemberService, loginHome.html 수정, 나머지파일들은 모두 이번에 새로 생성됨.

#### 10.23 14:40 혁상
회원가입 폼 수정 완료
회원 가입 폼에 맞게 회원가입 페이지, 컨트롤러 수정
MemberForm.java, MemberController.java, createMemberForm.html 수정

#### 10.23 17:00 혁상
홈이던 로그인 했을 때던 전체 아이템 목록이 바로 보이게 수정<br>
- 상품 등록할 때 아이템의 member컬럼에 판매자 계정이 등록되게 함

마이페이지 판매자 전환 기능 버튼 완료<br>
loginHome.html, home.html, mypageController.java 중점적으로 수정
홈페이지를 수정한 만큼 많은 부분이 수정되어 다 기재 불가..ㅎㅎ,,  

#### 10.28 21:00 석근
판매자용->내가올린 상품 보기 메뉴 추가  
memberStatus에 판단 후 판매자메뉴 보임/숨김 추가  
itemService,Repo / MypageController / mypage template 수정  

#### 10.28 23:30 석근
내 판매상품 -> 통계 기능 추가  
통계기능에는 각 판매상품별로 구매자 정보 및 total수익, 판매량을 보여줌( item domain에 판매량 추가함)  
OrderRepo, Service / Item.java - 판매량관련로직 / itemStats.html

#### 10.30 23:40 혁상
- 로그인 전 홈 화면에 로그인, 회원가입, 전체 상품 리스트만 나오게 함
- 로그인 후 홈 화면 상품목록 버튼 제거
- 각 페이지에 헤더 풋터 적용
- 로그인 전 홈 화면에는 아이템에 대한 모든 수정 버튼을 없애고 
  로그인 후 홈 화면에서는 현재 로그인된 아이디랑 구매자 아이디가 같을 때만 수정버튼 활성화
- 마이페이지와 로그인 후 화면에서 기능이 겹친다고 느낀 주석처리함 (추후 상의 필요)
- 전체 상품 리스트 클릭하면 리스트 디테일 페이지 나오게 함

#### 10.31 00:40 혁상
- 상품 리스트에서 상품을 눌렀을 때 그 상품의 디테일 페이지로 들어가서 거기서 주문이 되는 방식으로 추가
  (주문서에는 데이터베이스 id가 떠서 수정 필요함)  

#### 11.02 석근 
상품 Field 6종류 추가(수입 일자, 가공일, 원산지, 품종, 색깔, 맵기정도(스코빌지수))

-------------------------------------------------------------

*** 오류나 개선해야할 부분 ***
- 헤더에 회사 로고 이미지 변경 (완료)
- 상품 디테일 페이지에 글자 글자 크기 수정 (완료)
- 홈페이지에 소비자들을 위한 간단한 차트나 통계자료 필요 (완료)
- footer 분리 (완료)
- 국산고추, 해외고추 카테고리별로 볼 수 있으면 좋을 것 같다. (완료)
- 상품 수정 로직 구현 
- 회원 정보 수정 페이지 header 넣기
- Member-연령필드 - 통계에넣기
- Search 기능 활성화
- 가격 키로당 가격으로 바꾸기
- 상품 등록할 때 컬럼 추가 부분<br>
  -> 상품 설명 부분, 글 등록 날짜(시간), 가공 방법 (말림, 냉동 등)
   
- ★상품 필드 추가하면 Create, Update Form html + controller (create,update /createForm, UpdateForm) 빠뜨린거 없이 체크 해야됨...