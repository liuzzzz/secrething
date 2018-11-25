package com.secrething.adrift.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * Created by liuzz on 2018/11/22 下午4:35.
 */
@Controller
public class GreetingController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/chat")
    public void greeting(Message message) throws Exception {
        String sendTo = "/topic/greetings";
        messagingTemplate.convertAndSend(sendTo,HtmlUtils.htmlEscape(message.getContent()));
    }
}
