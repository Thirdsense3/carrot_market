package CarrotMarket.CarrotMarket.service;

import CarrotMarket.CarrotMarket.domain.Board;
import CarrotMarket.CarrotMarket.repository.BoardRepository;
import org.springframework.transaction.annotation.Transactional;

public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Board postBoard(Board board) {
        //TODO : XSS 대비 문자열 관리
        board.getText().replaceAll("[<>/]", "");
        return boardRepository.save(board);
    }
}
