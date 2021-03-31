package CarrotMarket.CarrotMarket.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    private String email;
    private String password;
    private String name;
    private Float locationX;
    private Float locationY;
    private String nickname;

    public Member()
    {}

    public Member(String email, String password, String name, Float locationX, Float locationY, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.locationX = locationX;
        this.locationY = locationY;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Float getLocationX() {
        return locationX;
    }

    public Float getLocationY() {
        return locationY;
    }

    public void setLocation(Float locationX, Float locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
