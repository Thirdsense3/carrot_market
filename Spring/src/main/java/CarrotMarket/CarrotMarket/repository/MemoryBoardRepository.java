package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.Board;

import java.lang.reflect.Array;
import java.util.*;

public class MemoryBoardRepository implements BoardRepository{

    private static Map<Long, Board> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Board save(Board board) {
        if(board.getId() == null) {
            board.setId(++sequence);
            store.put(board.getId(), board);
        }
        else {
            store.replace(board.getId(), board);
        }
        return board;
    }

    @Override
    public List<Board> load() {
        return new ArrayList<>(store.values());
    }

    /*@Override
    public List<Board> loadByLocation(String locationX, String locationY) {
        ArrayList<Board> result = new ArrayList<>();
        store.values().forEach( board -> {
            if(board.getLocationX().equals(locationX) && board.getLocationX().equals(locationY)) {
                result.add(board);
            }
        });
        return result;
    }*/

    @Override
    public List<Board> loadByCategory(int categoryId) {
        ArrayList<Board> result = new ArrayList<>();
        store.values().forEach( board -> {
            if(board.getCategoryId() == categoryId) {
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
    public List<Board> loadByNickname(String nickname) {
        ArrayList<Board> result = new ArrayList<>();
        store.values().forEach( board -> {
            if(board.getNickname().contains(nickname)) {
                result.add(board);
            }
        });
        return result;
    }

    @Override
    public List<Board> searchByWords(String text) {
        System.out.println("MemoryBoardRepository searchByWords():" + text);
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Board> searchBoardTitle(String title) {
        System.out.println("MemoryBoardRepository searchBoardTitle():" + title);
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void deleteById(Board board) {
        System.out.println("MemoryBoardRepository deleteById():" + board.getId());
    }
}