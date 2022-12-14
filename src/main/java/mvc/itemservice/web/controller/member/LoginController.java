package mvc.itemservice.web.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvc.itemservice.domain.member.Member;
import mvc.itemservice.domain.service.LoginService;
import mvc.itemservice.web.SessionConst;
import mvc.itemservice.web.dto.MemberLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("memberLoginDto") MemberLoginDto loginDto) {

        return "members/loginForm";
    }

    @PostMapping("/login")
    public String loginV3(@Validated @ModelAttribute MemberLoginDto loginDto,
                          BindingResult bindingResult, HttpServletRequest request,
                          @RequestParam(defaultValue = "/") String redirectURL) {

        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }

        Member loginMember = loginService.login(loginDto.getLoginId(), loginDto.getPassword());
        log.info("login? {}", loginMember);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }

        // 로그인 성공 처리

        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보를 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        // redirectURL 적용
        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {

        // 세션을 삭제.
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }






/*
    private final SessionManager sessionManager;


//    @PostMapping("/login")
    public String loginV2(@Validated @ModelAttribute MemberLoginDto loginDto,
                          BindingResult bindingResult, HttpServletResponse response){

        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }

        Member loginMember = loginService.login(loginDto.getLoginId(), loginDto.getPassword());
        log.info("login? {}", loginMember);

        if(loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }

        // 로그인 성공 처리
        // 세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
        sessionManager.createSession(loginMember, response);

        return "redirect:/";

//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {

        sessionManager.expire(request);

        return "redirect:/";
    }

//    @PostMapping("/login")
    public String login(@ModelAttribute @Validated MemberLoginDto loginDto,
                        BindingResult bindingResult, HttpServletResponse response) {

        if(bindingResult.hasErrors()) {
            return "members/loginForm";
        }

        Member loginMember = loginService.login(loginDto.getLoginId(), loginDto.getPassword());
        log.info("login ? {}", loginMember);

        if(loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }

        // 로그인 성공 처리할 예정 TODO

        // 쿠키에 시간 정보를 주지 않으면 세션 쿠키
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {

        expireCookie(response, "memberId");
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }*/
}