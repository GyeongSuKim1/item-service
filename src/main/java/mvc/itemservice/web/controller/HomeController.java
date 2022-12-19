package mvc.itemservice.web.controller;

import lombok.RequiredArgsConstructor;
import mvc.itemservice.domain.Repository.MemberRepository;
import mvc.itemservice.domain.member.Member;
import mvc.itemservice.web.SessionConst;
import mvc.itemservice.web.argumentresolver.Login;
import mvc.itemservice.web.session.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String homeLoginArgumentResolver(@Login Member loginMember, Model model) {

        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);

        return "loginHome";
    }









    /*
//    @GetMapping("/")
    public String homeLoginV4(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
            Member loginMember,
            Model model) {

        // 세션에 회원 데이터가 없으면 home
        if(loginMember == null) {
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);

        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        // 세션이 없으면 home
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member)
                session.getAttribute(SessionConst.LOGIN_MEMBER);
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);

        return "loginHome";
    }

    private final SessionManager sessionManager;
    private final MemberRepository memberRepository;

//    @GetMapping("/")
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
    }*/
}
