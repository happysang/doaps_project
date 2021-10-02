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

    private MemberStatus memberStatus;

    @Embedded
    private Address address;
}
