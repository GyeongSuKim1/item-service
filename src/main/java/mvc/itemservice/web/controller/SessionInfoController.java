package mvc.itemservice.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/sessionInfo")
    public String sessionInfo(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session == null) {
            return "세션이 없습니다.";
        }

        // 세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}",
                        name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        log.info("MaxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("CreationTime={}", new Date(session.getCreationTime()));
        log.info("LastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("inNew={}", session.isNew());

        return "세션 출력";
    }
}
