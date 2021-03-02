package CarrotMarket.CarrotMarket.controller;

import CarrotMarket.CarrotMarket.domain.Board;
import CarrotMarket.CarrotMarket.domain.CertificationCode;
import CarrotMarket.CarrotMarket.service.ListService;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ListController {
    private static final Logger logger = LoggerFactory.getLogger(ListController.class);

    private final ListService listService;

    @Autowired
    public ListController(ListService listService){
        this.listService = listService;
    }

    @RequestMapping(value = "board/list",method = RequestMethod.GET)
    @ResponseBody
    public List<Board> boardList(){
        List<Board> boardList = listService.getBoardList();
//        JSONObject jsonObject = new JSONObject();
//        try{
//            JSONArray jsonArray = new JSONArray();
//            for(int i=0;i<boardList.size();i++){
//                JSONObject jsonObject1 = new JSONObject();
//                jsonObject1.put("id",)
//            }
//        }
        return boardList;
    }
}
