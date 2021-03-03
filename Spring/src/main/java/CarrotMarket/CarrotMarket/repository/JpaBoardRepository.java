package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Board;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaBoardRepository implements BoardRepository{

    private final EntityManager em;

    public JpaBoardRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Board save(Board board) {
        if(board.getId() == null) {
            em.persist(board);
        } else {
            em.createQuery("update Board b set b.title = :title, b.text = :text, b.price = :price where b.id = :id")
                    .setParameter("title", board.getTitle())
                    .setParameter("text", board.getText())
                    .setParameter("price", board.getPrice())
                    .setParameter("id", board.getId())
                    .executeUpdate();
        }
        return board;
    }

    @Override
    public List<Board> load() {
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    @Override
    public List<Board> loadByLocation(String location) {
        return em.createQuery("select b from Board b where b.location = :location", Board.class)
                .setParameter("location", location)
                .getResultList();
    }

    @Override
    public List<Board> loadByCategory(String location, int categoryId) {
        return em.createQuery("select b from Board b where b.location = :location and b.categoryId = :categoryId", Board.class)
                .setParameter("location", location)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public List<Board> loadByTitle(String title) {
        return em.createQuery("select b from Board b where b.title like :title", Board.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }

    @Override
    public List<Board> loadByNickname(String nickname) {
        return em.createQuery("select b from Board b where b.nickname = :nickname", Board.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }
}
