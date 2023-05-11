import com.chatroom.client.entity.Message;
import com.chatroom.client.entity.MessageType;

/**
 * @program: chatroom
 * @description: 测试类
 * @author: 郭晨旭
 * @create: 2023-05-11 20:21
 * @version: 1.0
 **/
public class Test01 {
    @org.junit.Test
    public void contextLoads() {
        Message message = new Message();
        message.setMessageType(MessageType.COMMON_MESSAGE.getValue());
        System.out.println(message.getMessageType());
    }
}
