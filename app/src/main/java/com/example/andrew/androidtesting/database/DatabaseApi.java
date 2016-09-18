package com.example.andrew.androidtesting.database;

import com.example.andrew.androidtesting.message.Message;

import java.util.List;

public interface DatabaseApi {
    void saveMessage(Message message);

    Message getMessageById(long id) throws MessageNotFoundException;

    void deleteMessageById(long id);

    List<Message> getMessages();
}
