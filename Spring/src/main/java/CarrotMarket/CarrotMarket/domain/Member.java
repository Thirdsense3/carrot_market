package CarrotMarket.CarrotMarket.domain;

public class Member {

    private String Email;
    private String password;
    private String name;
    private String location;
    private String nickname;

    public Member(String email, String password, String name, String location, String nickname) {
        this.Email = email;
        this.password = password;
        this.name = name;
        this.location = location;
        this.nickname = nickname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
