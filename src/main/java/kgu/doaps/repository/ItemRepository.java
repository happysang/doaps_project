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

    public List<Item> findByOrigin(String origin) {
        return em.createQuery("select i from Pepper i where i.origin =:origin", Item.class)
                .setParameter("origin", origin)
                .getResultList();
    }
}
