package mvc.itemservice.web.controller;

import lombok.RequiredArgsConstructor;
import mvc.itemservice.domain.Repository.MemberRepository;
import mvc.itemservice.domain.member.Member;
import mvc.itemservice.web.session.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model) {

        Member member = (Member)sessionManager.getSession(request);
        if(member == null) {
            return "home";
        }

        // 로그인
        model.addAttribute("member", member);
        return "loginHome";
    }


//    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false)
                                Long memberId, Model model) {

        if (memberId == null) {
            return "home";
        }

        //로그인
        Member loginMember = memberRepository.findById(memberId);
        if(loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
