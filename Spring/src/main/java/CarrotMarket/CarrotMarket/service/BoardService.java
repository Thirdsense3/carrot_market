package CarrotMarket.CarrotMarket.service;

import CarrotMarket.CarrotMarket.domain.Board;
import CarrotMarket.CarrotMarket.repository.BoardRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Board postBoard(Board board) {
        //TODO : XSS 대비 문자열 관리
        //board.setText(board.getText().replaceAll("[<>/]", ""));
        System.out.println(board.getPrice());
        System.out.println(board.getCategoryId());
        System.out.println(board.getTitle());
        System.out.println(board.getText());
        System.out.println(board.getRegisterDate());
        System.out.println(board.getDeadlineDate());
        System.out.println(board.getChatCnt());
        System.out.println(board.getDibsCnt());
        System.out.println(board.getViewCnt());
        System.out.println(board.getPicture());
        return boardRepository.save(board);
    }

    public List<Board> searchBoard(String text) {
        return boardRepository.searchByWords(text);
    }

    @Transactional
    public Board editBoard(Board board) {
        Board preBoard = boardRepository.findById(board.getId()).get();
        preBoard.setTitle(board.getTitle());
        preBoard.setText(board.getText());
        preBoard.setPrice(board.getPrice());
        boardRepository.save(preBoard);
        return preBoard;
    }

    public void deleteImages(Long id) {

        File file = new File("C:\\images\\" + id);
        File[] files = file.listFiles();
        for(int i=0; i<files.length; i++) {
            files[i].delete();
        }
        file.delete();
    }

    public void deleteImages(Long id, String[] filename) {

        for(int i=0; i<filename.length; i++) {
            File file = new File("C:\\images\\" + id + "\\" + filename[i] + ".jpg");
            file.delete();
        }
    }

    public List<Board> findMyBoard(String nickname) {
        return boardRepository.loadByNickname(nickname);
    }

    public Optional<Board> findPostById(Long id) {
        return boardRepository.findById(id);
    }

    public void deleteById(Board board) {
        boardRepository.deleteById(board);
    }
}
