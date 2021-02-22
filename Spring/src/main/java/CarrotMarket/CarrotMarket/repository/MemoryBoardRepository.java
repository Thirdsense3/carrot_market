package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryBoardRepository implements BoardRepository{

    private static Map<Long, Board> store = new HashMap<>();

    @Override
    public boolean save(Board board) {
        //TODO
        return false;
    }

    @Override
    public List<Board> load() {
        //TODO
        return null;
    }

    @Override
    public List<Board> loadByLocation(String location) {
        //TODO
        return null;
    }

    @Override
    public List<Board> loadByCategory(String location, int categoryId) {
        //TODO
        return null;
    }

    @Override
    public List<Board> loadByTitle(String title) {
        //TODO
        return null;
    }

    @Override
    public Optional<Board> findById(Long id) {
        //TODO
        return Optional.empty();
    }
}
