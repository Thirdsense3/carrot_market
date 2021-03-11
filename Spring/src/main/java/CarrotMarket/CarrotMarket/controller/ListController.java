package CarrotMarket.CarrotMarket.controller;

import CarrotMarket.CarrotMarket.domain.Board;
import CarrotMarket.CarrotMarket.service.ListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class ListController {
    private static final Logger logger = LoggerFactory.getLogger(ListController.class);

    private final ListService listService;

    @Autowired
    public ListController(ListService listService){
        this.listService = listService;
    }

    @RequestMapping(value = "/board/list",method = RequestMethod.GET)
    @ResponseBody
    public List<Board> boardList(){
        List<Board> boardList = listService.getBoardList();
        return boardList;
    }

    @RequestMapping(value = "/board/list/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Optional<Board> getBoard(@PathVariable int id, Optional<Board> board){
        return board;
    }
}
