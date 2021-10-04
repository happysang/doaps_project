package kgu.doaps.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private String id;

    private String pwd;

    private String name;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @Embedded
    private Address address;

    //후에 개발 시 필드 추가할목록
//    private String phone;  //핸드폰번호
//    private String userID;  //로그인시 필요한 id
}
