package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    boolean save(Board board);
    List<Board> load();
    List<Board> loadByLocation(String location);
    List<Board> loadByCategory(String location, int categoryId);
    List<Board> loadByTitle(String title);
    Optional<Board> findById(Long id);
}