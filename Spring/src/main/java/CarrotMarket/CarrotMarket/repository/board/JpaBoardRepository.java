package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Board;
import org.springframework.transaction.annotation.Transactional;

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
        if(board.getId() == null) {
            em.persist(board);
        } else {
            em.createQuery("update Board b set b.title = :title, b.text = :text, b.price = :price, b.picture = :picture where b.id = :id")
                    .setParameter("title", board.getTitle())
                    .setParameter("text", board.getText())
                    .setParameter("price", board.getPrice())
                    .setParameter("id", board.getId())
                    .setParameter("picture", board.getPicture())
                    .executeUpdate();
        }
        return board;
    }

    @Override
    public List<Board> load() {
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    /*// 수정 필요
    @Override
    public List<Board> loadByLocation(String location) {
        return em.createQuery("select b from Board b where b.location = :location", Board.class)
                .setParameter("location", location)
                .getResultList();
    }*/

    @Override
    public List<Board> loadByCategory(int categoryId) {
        return em.createQuery("select b from Board b where b.categoryId = :categoryId", Board.class)
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
    public List<Board> searchByWords(String text) {
        System.out.println("searchByWords: " + text);
        return em.createQuery("select b from Board b where b.title like :text or b.text like :text", Board.class)
                .setParameter("text", text)
                .getResultList();
    }

    @Override
    public List<Board> searchBoardTitle(String title) {
        System.out.println("searchBoardTitle: " + title);
        return em.createQuery("select b from Board b where b.title like :title", Board.class)
                .setParameter("title", title)
                .getResultList();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }

    @Transactional
    @Override
    public void deleteById(Board board) {
        em.createQuery("DELETE FROM Board b WHERE b.id = :id").setParameter("id", board.getId()).executeUpdate();
        System.out.println("JpaBoardRepository deleteById(): " + board.getId());
    }
}
