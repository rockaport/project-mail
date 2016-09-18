package com.rockaport.mobile.mail.database;

import android.util.Log;
import android.util.LongSparseArray;

import com.rockaport.mobile.mail.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryDatabase implements DatabaseApi {
    private static final String TAG = "MemoryDatabase";
    private static AtomicLong rowId = new AtomicLong(0);
    private static LongSparseArray<Message> messages = new LongSparseArray<>();

    @Override
    public void saveMessage(Message message) {
        Log.d(TAG, "saveMessage() called with: message = [" + message + "]");

        if (message.isNew()) {
            message.setId(rowId.incrementAndGet());
        }

        messages.put(message.getId(), message);
    }

    @Override
    public Message getMessageById(long id) throws MessageNotFoundException {
        Log.d(TAG, "getMessageById() called with: id = [" + id + "]");

        Message message = messages.get(id);

        if (message == null) {
            throw new MessageNotFoundException();
        }

        return message;
    }

    @Override
    public void deleteMessageById(long id) {
        Log.d(TAG, "deleteMessageById() called with: id = [" + id + "]");

        messages.remove(id);
    }

    @Override
    public List<Message> getMessages() {
        Log.d(TAG, "getMessages() called");

        ArrayList<Message> messagesList = new ArrayList<>(messages.size());

        for (int i = 0; i < messages.size(); i++) {
            messagesList.add(messages.get(messages.keyAt(i)));
        }

        return messagesList;
    }
}
