package kgu.doaps;

import kgu.doaps.domain.Address;
import kgu.doaps.domain.GenderStatus;
import kgu.doaps.domain.Member;
import kgu.doaps.domain.MemberStatus;
import kgu.doaps.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;

    @PostConstruct
    public void init(){
        Member member = new Member();
        member.setLoginId("id1");
        member.setPassword("1234");
        member.setName("name1");
        member.setPhone("01012345678");
        member.setGenderStatus(GenderStatus.MALE);
        member.setMemberStatus(MemberStatus.SELLER);
        member.setAddress(new Address("서울시", "송파구", "방이동"));
        memberService.join(member);
    }
}
