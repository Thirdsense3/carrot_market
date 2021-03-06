package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    Board save(Board board);
    List<Board> load();
    //List<Board> loadByLocation(String location);
    List<Board> loadByCategory(int categoryId);
    List<Board> loadByTitle(String title);
    List<Board> loadByNickname(String nickname);
    List<Board> searchByWords(String text);
    List<Board> searchBoardTitle(String title);
    Optional<Board> findById(Long id);
    void deleteById(Board board);
}