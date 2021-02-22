package CarrotMarket.CarrotMarket.service;

import CarrotMarket.CarrotMarket.domain.Board;
import CarrotMarket.CarrotMarket.repository.BoardRepository;

public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Boolean postBoard(Board board) {
        //TODO : XSS 대비 문자열 관리
        return boardRepository.save(board);
    }
}
