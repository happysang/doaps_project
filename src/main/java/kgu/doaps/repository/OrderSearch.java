package kgu.doaps.repository;

import kgu.doaps.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName; //회원이름
    private OrderStatus orderStatus; // 주문 상대 [ORDER, CANCEL]

    //이후 검색할 필드를 추가할 것.
}
