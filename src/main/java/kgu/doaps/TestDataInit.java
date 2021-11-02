package kgu.doaps;

import kgu.doaps.domain.Address;
import kgu.doaps.domain.GenderStatus;
import kgu.doaps.domain.Member;
import kgu.doaps.domain.MemberStatus;
import kgu.doaps.domain.item.Item;
import kgu.doaps.domain.item.Pepper;
import kgu.doaps.service.ItemService;
import kgu.doaps.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.YearMonth;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final ItemService itemService;

    @PostConstruct
    public void init(){
        Member member1 = new Member();
        member1.setLoginId("id1");
        member1.setPassword("1234");
        member1.setName("name1");
        member1.setPhone("01012345678");
        member1.setGenderStatus(GenderStatus.MALE);
        member1.setMemberStatus(MemberStatus.SELLER);
        member1.setAddress(new Address("서울시", "송파구", "방이동"));
        memberService.join(member1);

        Member member2 = new Member();
        member2.setLoginId("id2");
        member2.setPassword("1234");
        member2.setName("name2");
        member2.setPhone("01011111111");
        member2.setGenderStatus(GenderStatus.MALE);
        member2.setMemberStatus(MemberStatus.SELLER);
        member2.setAddress(new Address("서울시", "강남구", "대치동"));
        memberService.join(member2);

        Pepper pepper = new Pepper();
        pepper.setName("상품1");
        pepper.setPrice(10000);
        pepper.setMember(member1);
        pepper.setStockQuantity(1000);
        pepper.setSpicy(1000);
        pepper.setVariety("청양");
        pepper.setImportDate(YearMonth.of(2021,2));
        pepper.setProcessDate(YearMonth.of(2021,3));
        pepper.setColor("#bf1d1d");
        itemService.saveItem(pepper);

    }
}
