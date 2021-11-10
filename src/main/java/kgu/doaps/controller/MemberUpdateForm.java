package kgu.doaps.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberUpdateForm {
    private Long id;
    @NotEmpty(message = "회원 이름 입력은 필수 입니다")
    private String name;
    @NotEmpty(message = "주소 입력은 필수 입니다")
    private String city;
    @NotEmpty(message = "주소 입력은 필수 입니다")
    private String street;
    @NotEmpty(message = "주소 입력은 필수 입니다")
    private String zipcode;
    private String phone;

}
