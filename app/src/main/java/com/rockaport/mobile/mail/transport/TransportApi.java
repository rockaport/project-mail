package com.rockaport.mobile.mail.transport;

import com.rockaport.mobile.mail.models.message.Message;

import java.util.List;

public interface TransportApi {
    void send(Message message);

    List<Message> receive();
}
