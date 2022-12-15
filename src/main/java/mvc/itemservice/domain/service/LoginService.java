package mvc.itemservice.domain.service;

import lombok.RequiredArgsConstructor;
import mvc.itemservice.domain.Repository.MemberRepository;
import mvc.itemservice.domain.member.Member;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    // return null 이면 로그인 실패
    public Member login(String loginId, String password) {

//        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
//        Member member = findMemberOptional.get();
//        if(member.getPassword().equals(password)) {
//            return member;
//        } else {
//            return null;
//        }
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
