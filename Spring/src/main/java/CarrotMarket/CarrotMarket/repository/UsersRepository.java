package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Users;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    Users save(Users users);
    List<Users> load();
    Optional<Users> findById(Long user_id);
    List<Users> findByEmail(String email);
    List<Users> findByNickName(String nickname);
}
