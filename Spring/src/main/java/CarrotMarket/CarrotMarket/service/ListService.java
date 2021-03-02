package CarrotMarket.CarrotMarket.service;

import CarrotMarket.CarrotMarket.domain.Board;
import CarrotMarket.CarrotMarket.repository.BoardRepository;
import CarrotMarket.CarrotMarket.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListService {

    @Autowired
    private final BoardRepository boardRepository;

    public ListService(BoardRepository boardRepository) {this.boardRepository = boardRepository;}

    public List<Board> getBoardList(){
        List<Board> boardList = boardRepository.load();
        return boardList;
    }

    public void getBoard(){
        //TODO ("게시물 열람 및 조회수 증가")
    }

    public void deleteBoard(){
        //TODO ("게시물 삭제")
    }

    public void autoDeleteBoard(){
        //TODO ("게시물 유효기간 만료로 인한 삭제)
    }

}
