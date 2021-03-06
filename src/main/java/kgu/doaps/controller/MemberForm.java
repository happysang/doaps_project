package kgu.doaps.controller;

import kgu.doaps.domain.GenderStatus;
import kgu.doaps.domain.MemberStatus;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
@Getter @Setter
public class MemberForm {
    @NotEmpty(message = "아이디 입력은 필수 입니다")
    private String loginId;
    @NotEmpty(message = "비밀번호 입력은 필수 입니다")
    private String password;
    @NotEmpty(message = "회원 이름 입력은 필수 입니다")
    private String name;
    @NotEmpty(message = "전화번호 입력은 필수 입니다")
    private String phone;
//    @NotEmpty(message = "성별 선택은 필수 입니다")
    private GenderStatus genderStatus;
//    @NotEmpty(message = "계정 타입 선택은 필수 입니다")
    private MemberStatus memberStatus;
    @NotEmpty(message = "주소 입력은 필수 입니다")
    private String city;
    @NotEmpty(message = "주소 입력은 필수 입니다")
    private String street;
    @NotEmpty(message = "주소 입력은 필수 입니다")
    private String zipcode;

    private String age;

}