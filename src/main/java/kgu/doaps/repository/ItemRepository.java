package kgu.doaps.repository;

import kgu.doaps.domain.Order;
import kgu.doaps.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) { return em.find(Item.class, id); }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    public List<Item> findMyItem(Long id) {
        return em.createQuery("select i from Item i where i.member.id =:id", Item.class)
                .setParameter("id", id)
                .getResultList();
    }
    /*
    추가할 메서드
     */
    /*
    public List<Item> findAll(ItemSearch itemSearch) { } // 물품 검색 기능 ( + ItemSearch class 생성필요)
    */
}
