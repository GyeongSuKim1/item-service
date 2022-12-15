package mvc.itemservice.web;

import lombok.RequiredArgsConstructor;
import mvc.itemservice.domain.Repository.ItemRepository;
import mvc.itemservice.domain.Repository.MemberRepository;
import mvc.itemservice.domain.item.Item;
import mvc.itemservice.domain.item.ItemType;
import mvc.itemservice.domain.member.Member;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    // 테스트용 데이터
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("TestProduct-A", 10000, 10,
                true, "SEOUL", ItemType.ETC, "FAST"));
        itemRepository.save(new Item("TestProduct-B", 20000, 20,
                false, "BUSAN", ItemType.BOOK, "NORMAL"));

        Member member = new Member();
        Member memberTwo = new Member();
        member.setLoginId("a");
        member.setName("열두시");
        member.setPassword("a");

        memberTwo.setLoginId("q");
        memberTwo.setName("아홉시");
        memberTwo.setPassword("q");

        memberRepository.save(member);
        memberRepository.save(memberTwo);
    }
}
