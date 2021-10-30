package kgu.doaps.repository;

import kgu.doaps.domain.Member;
import kgu.doaps.domain.Order;
import kgu.doaps.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) { em.persist(order);}

    public Order findOne(Long id) { return em.find(Order.class, id); }

    //조건 검색 기능 (강의 내용)
    public List<Order> findAll(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" +
                            orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }

    public List<Order> findMyOrder(Long userId) {
        return em.createQuery("select o from Order o where o.member.id =:id", Order.class)
                .setParameter("id", userId)
                .getResultList();
    }

    public List<Order> findByItem(Long itemId) {
        List<OrderItem> orderItems = em.createQuery("select oi from OrderItem oi where oi.item.id = :itemId", OrderItem.class)
                .setParameter("itemId", itemId)
                .getResultList();
        // itemId로 orderItems찾은 후 Order를 찾아서 리턴함
        // ★한번에 여러개를 시키도록 코드를 변경할 경우 Order가 여러번 찾아지기 떄문에 요녀석도 같이 변경해야한다.~~
        List<Order> orders = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            orders.add(em.find(Order.class, orderItem.getOrder().getId()));
        }

        return orders;
    }
}
