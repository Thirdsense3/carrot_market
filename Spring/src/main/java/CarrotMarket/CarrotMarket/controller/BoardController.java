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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
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
        if(board.getId() != null)
            newBoard.setId(board.getId());
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
        int cnt = 0;
        for(var i : imageFile) {
            System.out.println("이미지 업로드 : " + i.getOriginalFilename());
            i.transferTo(new File(baseDir + i.getOriginalFilename() + ".jpg"));

            if(cnt == 0) {
                try {
                    int newWidth = 200;
                    int newHeight = 200;

                    Image image = ImageIO.read(new File(baseDir + i.getOriginalFilename() + ".jpg"));
                    Image previewImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);

                    BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics g = newImage.getGraphics();
                    g.drawImage(previewImage, 0, 0, null);
                    g.dispose();
                    ImageIO.write(newImage, "jpg", new File(baseDir + "previewImage.jpg"));
                    System.out.println("변환성공");
                } catch (Exception e) {
                    System.out.println("변환실패 : " + e.toString());
                }
            }

            cnt++;
        }
        System.out.println("picture Upload");
        return "success";
    }

    @GetMapping("/download/{id}/{filename}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("id") Long id, @PathVariable("filename") String filename) throws IOException {
        String baseDir = "C:\\images\\";
        Path path = Paths.get(baseDir + id + "\\" + filename + ".jpg");
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + ".jpg")
                .body(resource);
    }
    
    @GetMapping("/download/{id}/preview")
    public ResponseEntity<Resource> previewImageDownload(@PathVariable("id") Long id) throws IOException {
        String baseDir = "C:\\images\\";
        Path path = Paths.get(baseDir + id + "\\previewImage.jpg");
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "preview.jpg")
                .body(resource);
    }

    @PostMapping("/board/delete")
    @ResponseBody
    public String DeleteBoard(Board board) {
        System.out.println("/board/delete:" + board.getId());
        boardService.deleteImages(board.getId());
        boardService.deleteById(board);
        return "ok";
    }
}
