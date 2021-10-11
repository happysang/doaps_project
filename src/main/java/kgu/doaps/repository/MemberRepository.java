package kgu.doaps.repository;

import kgu.doaps.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    /*
    * save method : 멤버 정보 수정 기능 탑재 하면서 변경 필수
     */
    public void save(Member member) { em.persist(member); }

    public Member findOne(Long id) { return em.find(Member.class, id); }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Member findByLoginId(String loginId) {
        List<Member> findMember = em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList();
        if (findMember.size()==0){
            return null;
        } else{
            return findMember.get(0);
        }
    }
}
