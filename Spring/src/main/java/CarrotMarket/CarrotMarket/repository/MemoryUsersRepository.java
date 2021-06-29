package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Users;

import java.util.*;

public class MemoryUsersRepository implements UsersRepository {

    private static Map<Long, Users> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Users save(Users users) {
        if (users.getId() == null) {
            users.setId(++sequence);
            store.put(users.getId(), users);
        }
        else {
            store.replace(users.getId(), users);
        }
        return users;
    }

    @Override
    public List<Users> load() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Users> findById(Long user_id) {
        return Optional.ofNullable(store.get(user_id));
    }

    @Override
    public List<Users> findByEmail(String email) {
        ArrayList<Users> result = new ArrayList<>();
        store.values().forEach( users -> {
            if(users.getEmail().contains(email)) {
                result.add(users);
            }
        });
        return result;
    }

    @Override
    public List<Users> findByNickName(String nickname) {
        ArrayList<Users> result = new ArrayList<>();
        store.values().forEach( users -> {
            if(users.getNickname().contains(nickname)) {
                result.add(users);
            }
        });
        return result;
    }
}
