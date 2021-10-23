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


#### 10.23 14:40 혁상
회원가입 폼 수정 완료
회원 가입 폼에 맞게 회원가입 페이지, 컨트롤러 수정
MemberForm.java, MemberController.java, createMemberForm.html 수정