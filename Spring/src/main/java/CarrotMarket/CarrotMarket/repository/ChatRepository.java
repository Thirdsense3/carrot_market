package CarrotMarket.CarrotMarket.repository;

import CarrotMarket.CarrotMarket.domain.ChatMessage;
import CarrotMarket.CarrotMarket.domain.ChatRoom;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository {
    List<ChatMessage> findAllRoom();
    ChatRoom findRoomById(int id);

    /**
     * TODO("채팅방 생성 함수 작성")
     * */
//    ChatRoom createChatRoom(int roomId)

}
