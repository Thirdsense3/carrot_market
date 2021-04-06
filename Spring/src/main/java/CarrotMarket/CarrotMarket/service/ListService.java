package CarrotMarket.CarrotMarket.service;

import CarrotMarket.CarrotMarket.domain.Board;
import CarrotMarket.CarrotMarket.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ListService {

    @Autowired
    private final BoardRepository boardRepository;

    public ListService(BoardRepository boardRepository) {this.boardRepository = boardRepository;}

    public List<Board> getBoardList(){
        List<Board> boardList = boardRepository.load();
        return boardList;
    }

    public Optional<Board> getBoard(Long id){
        Optional<Board> board = boardRepository.findById(id);
        if(board.isPresent()){
            int viewCnt = board.get().getViewCnt();
            board.get().setViewCnt(viewCnt+1);
            System.out.println(board.get().getId());
            System.out.println(board.get().getTitle());
            return board;
        }
        else{
            return Optional.empty();
        }
        //TODO ("게시물 열람 및 조회수 증가")
    }

    public void deleteBoard(){
        //TODO ("게시물 삭제")
    }

    public void autoDeleteBoard(){
        //TODO ("게시물 유효기간 만료로 인한 삭제)
    }

}
