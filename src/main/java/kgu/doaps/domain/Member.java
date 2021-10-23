package kgu.doaps.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id; // 데이터베이스 ID

    @NotEmpty
    private String loginId; // 로그인 ID

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;
    
    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private GenderStatus genderStatus;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;
}
