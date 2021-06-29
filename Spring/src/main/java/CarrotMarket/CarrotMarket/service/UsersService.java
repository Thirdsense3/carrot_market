package CarrotMarket.CarrotMarket.service;

import CarrotMarket.CarrotMarket.domain.Users;
import CarrotMarket.CarrotMarket.repository.UsersRepository;
import org.springframework.transaction.annotation.Transactional;

public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public Users registerUsers(Users users) {
        System.out.println(users.getId());
        System.out.println(users.getEmail());
        System.out.println(users.getPassword());
        System.out.println(users.getName());
        System.out.println(users.getNickname());
        System.out.println(users.getToken());

        return usersRepository.save(users);
    }
}
