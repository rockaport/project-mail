package com.rockaport.mobile.mail.database;

import android.util.Log;
import android.util.LongSparseArray;

import com.rockaport.mobile.mail.message.Attachment;
import com.rockaport.mobile.mail.message.Message;
import com.rockaport.mobile.mail.message.MessageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryDatabase implements DatabaseApi {
    private static final String TAG = "MemoryDatabase";
    private static AtomicLong messageRowId = new AtomicLong(0);
    private static AtomicLong attachmentRowId = new AtomicLong(0);
    private static LongSparseArray<Message> messages = new LongSparseArray<>();
    private static LongSparseArray<Attachment> attachments = new LongSparseArray<>();

    private static MemoryDatabase instance = null;

    public static MemoryDatabase getInstance() {
        if (instance == null) {
            instance = new MemoryDatabase();
        }

        return instance;
    }

    private MemoryDatabase() {
        for (Message message : MessageUtil.generateRandomMessages()) {
            saveMessage(message);
        }
    }

    @Override
    public void saveMessage(Message message) {
        Log.d(TAG, "saveMessage() called with: message = [" + message + "]");

        if (message.isNew()) {
            message.setId(messageRowId.incrementAndGet());
        }

        messages.put(message.getId(), message);

        for (Attachment attachment : message.getAttachments()) {
            attachment.setMessageId(message.getId());
            saveAttachment(attachment);
        }
    }

    @Override
    public Message getMessageById(long id) throws MessageNotFoundException {
        Log.d(TAG, "getMessageById() called with: id = [" + id + "]");

        Message message = messages.get(id);

        if (message == null) {
            throw new MessageNotFoundException();
        }

        message.setAttachments(getAttachmentsByMessageId(message.getId()));

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

    @Override
    public void saveAttachment(Attachment attachment) {
        if (attachment.isNew()) {
            attachment.setId(attachmentRowId.incrementAndGet());
        }

        attachments.put(attachment.getId(), attachment);
    }

    @Override
    public Attachment getAttachmentById(long id) throws AttachmentNotFoundException {
        Attachment attachment = attachments.get(id);

        if (attachment == null) {
            throw new AttachmentNotFoundException();
        }

        return attachment;
    }

    @Override
    public List<Attachment> getAttachmentsByMessageId(long messageId) {
        List<Attachment> attachmentsList = new ArrayList<>();

        Attachment attachment;
        for (int i = 0; i < attachments.size(); i++) {
            attachment = attachments.get(attachments.keyAt(i));

            if (attachment.getMessageId() == messageId) {
                attachmentsList.add(attachment);
            }
        }

        return attachmentsList;
    }

    @Override
    public void deleteAttachmentById(long id) {
        attachments.delete(id);
    }

    @Override
    public void deleteAttachmentsByMessageId(long messageId) {
        List<Long> rowIds = new ArrayList<>();

        for (int i = 0; i < attachments.size(); i++) {
            if (attachments.get(attachments.keyAt(i)).getMessageId() == messageId) {
                rowIds.add(attachments.keyAt(i));
            }
        }

        for (long rowId : rowIds) {
            attachments.delete(rowId);
        }
    }
}
