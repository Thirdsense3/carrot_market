package CarrotMarket.CarrotMarket.controller;

import CarrotMarket.CarrotMarket.domain.Board;
import CarrotMarket.CarrotMarket.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/post")
    @ResponseBody
    public Board posting(Board board) {

        try {
            String text = board.getText();
            String location = board.getLocation();
            String title = board.getTitle();
            int categoryId = board.getCategoryId();
            String registerDate = board.getRegisterDate();
            String nickname = board.getNickname();
            Long price = board.getPrice();
            String deadlineDate = board.getDeadlineDate();

            Board board1 = new Board(price, title, text, categoryId, nickname, registerDate, deadlineDate, location);
            boardService.postBoard(board1);

        } catch(Exception e) {
            System.out.println("포스팅 오류");
        }

        return board;
    }
}
