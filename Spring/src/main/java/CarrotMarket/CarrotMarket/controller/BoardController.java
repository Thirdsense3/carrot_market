package CarrotMarket.CarrotMarket.controller;

import CarrotMarket.CarrotMarket.domain.Board;
import CarrotMarket.CarrotMarket.service.BoardService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
            Float locationX = board.getLocationX();
            Float locationY = board.getLocationY();
            String title = board.getTitle();
            int categoryId = board.getCategoryId();
            String registerDate = board.getRegisterDate();
            String nickname = board.getNickname();
            Long price = board.getPrice();
            String deadlineDate = board.getDeadlineDate();

            Board board1 = new Board(price, title, text, categoryId, nickname, registerDate, deadlineDate, locationX, locationY);
            boardService.postBoard(board1);

        } catch(Exception e) {
            System.out.println("포스팅 오류");
        }

        return board;
    }

    @GetMapping("/myBoard/{nickname}")
    @ResponseBody
    public List<Board> getMyBoard(@PathVariable String nickname) {
        return boardService.findMyBoard(nickname);
    }

    @PostMapping("/edit")
    @ResponseBody
    public Board editBoard(Board board) {
        boardService.editBoard(board);
        return board;
    }

    @PostMapping("board/posting")
    @ResponseBody
    public Board PostBoard(Board board) {
        String baseDir = "C:\\images\\";
        Board newBoard = new Board(board.getPrice(), board.getTitle(), board.getText(), board.getCategoryId(), board.getNickname(), board.getRegisterDate(), board.getDeadlineDate(), board.getLocationX(), board.getLocationY());
        newBoard.setPicture(board.getPicture());
        boardService.postBoard(newBoard);
        return newBoard;
    }

    @PostMapping("/board/picture")
    @ResponseBody
    public String UploadImage(Long id, @RequestBody ArrayList<MultipartFile> imageFile) throws Exception {
        String baseDir = "C:\\images\\" + id + "\\";
        File file = new File(baseDir);
        if(!file.exists()) {
            System.out.println("폴더 생성");
            System.out.println(file.toString());
            file.mkdir();
        }
        //imageFile.transferTo(new File(baseDir + imageFile.getOriginalFilename() + ".jpg"));
        for(var i : imageFile) {
            System.out.println("이미지 업로드 : " + i.getOriginalFilename());
            i.transferTo(new File(baseDir + i.getOriginalFilename()));
        }
        System.out.println("picture Upload");
        return "success";
    }

    @GetMapping("/download/{id}/{filename}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("id") Long id, @PathVariable("filename") String filename) throws IOException {
        String baseDir = "C:\\images\\";
        Path path = Paths.get(baseDir + id + "\\" + filename);
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename)
                .body(resource);
    }
}
