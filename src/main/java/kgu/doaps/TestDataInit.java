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
        member1.setAge("25");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setLoginId("id2");
        member2.setPassword("1234");
        member2.setName("name2");
        member2.setPhone("01011111111");
        member2.setGenderStatus(GenderStatus.MALE);
        member2.setMemberStatus(MemberStatus.SELLER);
        member2.setAddress(new Address("서울시", "강남구", "대치동"));
        member2.setAge("30");
        memberService.join(member2);

        Pepper pepper1 = new Pepper();
        pepper1.setName("상품1");
        pepper1.setPrice(10000);
        pepper1.setMember(member1);
        pepper1.setStockQuantity(1000);
        pepper1.setSpicy(1000);
        pepper1.setVariety("청양");
        pepper1.setImportDate(YearMonth.of(2021,2));
        pepper1.setProcessDate(YearMonth.of(2021,3));
        pepper1.setColor("#bf1d1d");
        itemService.saveItem(pepper1);

        Pepper pepper2 = new Pepper();
        pepper2.setName("상품2");
        pepper2.setPrice(1000);
        pepper2.setMember(member2);
        pepper2.setStockQuantity(1000);
        pepper2.setSpicy(1000);
        pepper2.setOrigin("중국");
        pepper2.setImgUrl("upload/IMG_9080.JPG");
        pepper2.setVariety("청양");
        pepper2.setImportDate(YearMonth.of(2021,2));
        pepper2.setProcessDate(YearMonth.of(2021,3));
        pepper2.setColor("#bf1d1d");
        itemService.saveItem(pepper2);

    }
}
