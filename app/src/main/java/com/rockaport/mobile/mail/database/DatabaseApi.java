package com.rockaport.mobile.mail.database;

import com.rockaport.mobile.mail.message.Message;

import java.util.List;

public interface DatabaseApi {
    void saveMessage(Message message);

    Message getMessageById(long id) throws MessageNotFoundException;

    void deleteMessageById(long id);

    List<Message> getMessages();
}
