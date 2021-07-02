package CarrotMarket.CarrotMarket.domain;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String token;

    public Users()
    {}

    public Users(String email, String password, String name, String nickname, String token) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.token = token;
    }

    public Long getId() {
        return this.user_id;
    }

    public void setId(Long user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return this.password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() { return this.token; }

    public void setToken(String token) { this.token = token; }
}