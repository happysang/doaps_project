package kgu.doaps.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    private String pwd;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;


    //후에 개발 시 필드 추가할목록
//    private String phone;  //핸드폰번호
//    private String userID;  //로그인시 필요한 id
}
