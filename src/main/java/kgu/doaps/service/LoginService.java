package kgu.doaps.service;

import kgu.doaps.domain.Member;
import kgu.doaps.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;


    public Member login(String memberId, String password) {
        Member member = memberRepository.findByLoginId(memberId);
        if (member == null){
            return null;
        }
        if (member.getPassword().equals(password)){
            return member;
        } else {
            return null;
        }
    }
}
