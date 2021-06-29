package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Users;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaUsersRepository implements UsersRepository {

    private final EntityManager em;

    public JpaUsersRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Users save(Users users) {
        em.persist(users);
        return users;
    }

    @Override
    public List<Users> load() {
        return em.createQuery("select m from Users m", Users.class).getResultList();
    }

    @Override
    public Optional<Users> findById(Long user_id) {
        Users users = em.find(Users.class, user_id);
        return Optional.ofNullable(users);
    }

    @Override
    public List<Users> findByEmail(String email) {
        List<Users> result = em.createQuery("select m from Users m where m.email = :email", Users.class)
                .setParameter("email", email)
                .getResultList();
        return result;
    }

    @Override
    public List<Users> findByNickName(String nickname) {
        List<Users> result = em.createQuery("select m from Users m where m.nickname = :nickname", Users.class)
                .setParameter("nickname", nickname)
                .getResultList();
        return result;
    }
}
