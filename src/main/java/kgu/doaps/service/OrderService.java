package kgu.doaps.service;

import kgu.doaps.domain.Delivery;
import kgu.doaps.domain.Member;
import kgu.doaps.domain.Order;
import kgu.doaps.domain.OrderItem;
import kgu.doaps.domain.item.Item;
import kgu.doaps.repository.ItemRepository;
import kgu.doaps.repository.MemberRepository;
import kgu.doaps.repository.OrderRepository;
import kgu.doaps.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 1 엔티티 조회
        Member member = memberRepository.findById(memberId);
        Item item = itemRepository.findOne(itemId);

        // 2 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 3 주문 상품 생성 단순화를 위해 일단 여러개가 아니라 단일 생성, 후에 멀티생성 코드로 수정
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 4 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 5 주문 저장 cascade.all 덕분에 orderItems 과 delivery 까지 persist 된다.
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    /**
     * 주문 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

    public List<Order> findMyOrder(Long id) { return orderRepository.findMyOrder(id); }

    public List<Order> findByItem(Long id) { return orderRepository.findByItem(id); }

}
