package kgu.doaps.service;

import kgu.doaps.domain.Address;
import kgu.doaps.domain.Member;
import kgu.doaps.domain.MemberStatus;
import kgu.doaps.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional // 12번째줄로 일단 전체 true 먹히게 한후 변경시켜주는 join만 false로 다시세팅
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional
    public void updateMember(Long memberId, String name, Address address) {
        Member findMember = memberRepository.findById(memberId);
        findMember.setName(name);
        findMember.setAddress(address);
        //★성별,폰번호 등등 추가해줄것.
    }

    @Transactional
    public void updateMemberStatus(Long memberId, MemberStatus memberStatus){
        Member findMember = memberRepository.findById(memberId);
        findMember.setMemberStatus(memberStatus);
        System.out.println("상태변경완료 = " + findMember.getMemberStatus());
    }


}
