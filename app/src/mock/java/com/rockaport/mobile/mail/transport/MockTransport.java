package com.rockaport.mobile.mail.transport;

import com.rockaport.mobile.mail.message.Message;
import com.rockaport.mobile.mail.message.MessageUtil;

import java.util.List;
import java.util.Random;

public class MockTransport implements TransportApi {
    private static MockTransport instance;

    public static MockTransport getInstance() {
        if (instance == null) {
            instance = new MockTransport();
        }

        return instance;
    }

    @Override
    public void send(Message message) {
        // no op
    }

    @Override
    public List<Message> receive() {
        return MessageUtil.generateRandomMessages(new Random().nextInt(3) + 1);
    }
}
