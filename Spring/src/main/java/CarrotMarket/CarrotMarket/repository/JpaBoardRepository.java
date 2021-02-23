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
        em.persist(board);
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
    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }
}
