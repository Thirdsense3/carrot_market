package CarrotMarket.CarrotMarket.domain;

import javax.annotation.processing.Generated;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@Entity
@Table(name = "BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;
    private String title;
    private String text;
    private int categoryId;
    private Float locationX;
    private Float locationY;
    private String nickname;
    private String registerDate;
    private String deadlineDate;
    private int dibsCnt;
    private int viewCnt;
    private int chatCnt;
    private String picture;
    public Board()
    {}

    public Board(Long price, String title, String text, int categoryId, String nickname, String registerDate, String deadlineDate, Float locationX, Float locationY) {
        this.price = price;
        this.title = title;
        this.text = text;
        this.categoryId = categoryId;
        this.nickname = nickname;
        this.dibsCnt = 0;
        this.viewCnt = 0;
        this.chatCnt = 0;
        this.registerDate = registerDate;
        this.deadlineDate = deadlineDate;
        this.locationX = locationX;
        this.locationY = locationY;
    }
    //private String picture;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /*public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }*/

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public int getDibsCnt() {
        return dibsCnt;
    }

    public void setDibsCnt(int dibsCnt) {
        this.dibsCnt = dibsCnt;
    }

    public int getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }

    public int getChatCnt() {
        return chatCnt;
    }

    public void setChatCnt(int chatCnt) {
        this.chatCnt = chatCnt;
    }
}
