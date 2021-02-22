package CarrotMarket.CarrotMarket.domain;

public class Board {

    private int id;
    private Long price;
    private String title;
    private String text;
    private int categoryId;
    private String location;
    private String nickname;
    private String registerDate;
    private String deadlineDate;
    private int dibsCnt;
    private int viewCnt;
    private int chatCnt;
    //private String picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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
