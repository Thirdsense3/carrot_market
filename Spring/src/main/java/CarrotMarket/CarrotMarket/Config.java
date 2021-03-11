package CarrotMarket.CarrotMarket;

import CarrotMarket.CarrotMarket.repository.*;
import CarrotMarket.CarrotMarket.service.BoardService;
import CarrotMarket.CarrotMarket.repository.BoardRepository;
import CarrotMarket.CarrotMarket.repository.MemberRepository;
import CarrotMarket.CarrotMarket.repository.MemoryBoardRepository;
import CarrotMarket.CarrotMarket.repository.MemoryMemberRepository;
import CarrotMarket.CarrotMarket.service.ListService;
import CarrotMarket.CarrotMarket.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class Config {

    private EntityManager em;

    @Autowired
    public Config(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        return new JpaMemberRepository(em);
    }

    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository());
    }

    @Bean
    public BoardRepository boardRepository() {
        //return new JpaBoardRepository(em);
        return new MemoryBoardRepository();
    }

    @Bean
    public ListService listService(){
        return  new ListService((boardRepository()));
    }

}
