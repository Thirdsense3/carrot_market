package CarrotMarket.CarrotMarket;

import CarrotMarket.CarrotMarket.repository.BoardRepository;
import CarrotMarket.CarrotMarket.repository.MemberRepository;
import CarrotMarket.CarrotMarket.repository.MemoryBoardRepository;
import CarrotMarket.CarrotMarket.repository.MemoryMemberRepository;
import CarrotMarket.CarrotMarket.service.ListService;
import CarrotMarket.CarrotMarket.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public ListService listService(){
        return  new ListService((boardRepository()));
    }

    @Bean
    public BoardRepository boardRepository(){
        return new MemoryBoardRepository();
    }
}
