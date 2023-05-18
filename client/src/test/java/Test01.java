import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.entity.User;
import com.chatroom.service.UserService;
import com.chatroom.service.impl.UserServiceImpl;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testSocket() throws IOException, ClassNotFoundException {
        UserService userService = new UserServiceImpl();
        Message msg = new Message();
        User user = new User();
        user.setUserId(12);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        String string = JSON.toJSONString(list);
        msg.setContent(string);
        msg.setMessageType(MessageType.LOGIN_BY_PWD.getValue());
        System.out.println(msg);
        userService.testSend(msg);
    }
}
