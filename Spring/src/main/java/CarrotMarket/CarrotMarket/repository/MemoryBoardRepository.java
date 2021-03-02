package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Board;

import java.lang.reflect.Array;
import java.util.*;

public class MemoryBoardRepository implements BoardRepository{

    private static Map<Long, Board> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Board save(Board board) {
        board.setId(++sequence);
        store.put(board.getId(), board);
        return board;
    }

    @Override
    public List<Board> load() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Board> loadByLocation(String location) {
        ArrayList<Board> result = new ArrayList<>();
        store.values().forEach( board -> {
            if(board.getLocation().equals(location)) {
                result.add(board);
            }
        });
        return result;
    }

    @Override
    public List<Board> loadByCategory(String location, int categoryId) {
        ArrayList<Board> result = new ArrayList<>();
        store.values().forEach( board -> {
            if(board.getLocation().equals(location) && board.getCategoryId() == categoryId) {
                result.add(board);
            }
        });
        return result;
    }

    @Override
    public List<Board> loadByTitle(String title) {
        ArrayList<Board> result = new ArrayList<>();
        store.values().forEach( board -> {
            if(board.getTitle().contains(title)) {
                result.add(board);
            }
        });
        return result;
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}