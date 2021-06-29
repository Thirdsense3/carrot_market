package CarrotMarket.CarrotMarket.controller;

import CarrotMarket.CarrotMarket.domain.Users;
import CarrotMarket.CarrotMarket.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/users/register")
    @ResponseBody
    public Users registerUsers(Users users) {
        System.out.println("registerUsers()");
        try {
            usersService.registerUsers(users);
        } catch (Exception e) {
            System.out.println("registerUsers() 오류 발생.");
            System.out.println(e);
        }
        return users;
    }
}
