package CarrotMarket.CarrotMarket.service;

import CarrotMarket.CarrotMarket.domain.Board;
import CarrotMarket.CarrotMarket.repository.BoardRepository;

import java.util.List;

public class ListService {
    BoardRepository boardRepository;

    public void getBoardList(){
        //TODO ("게시물 해당 지역 불러오기")
        List<Board> boardList = (List<Board>) boardRepository.load();
    }

    public void getBoard(){
        //TODO ("게시물 열람 및 조회수 증가")
    }

    public void searchBoardList(){
        //TODO ("게시물 카테고리 검색")
        //TODO ("게시물 제목 검색")
    }


}
