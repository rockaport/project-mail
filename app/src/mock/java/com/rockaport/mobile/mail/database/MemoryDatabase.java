package com.rockaport.mobile.mail.database;

import com.rockaport.mobile.mail.message.Attachment;
import com.rockaport.mobile.mail.message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryDatabase implements DatabaseApi {
    private static final String TAG = "MemoryDatabase";
    private static AtomicLong messageRowId = new AtomicLong(0);
    private static AtomicLong attachmentRowId = new AtomicLong(0);
    private static HashMap<Long, Message> messages = new HashMap<>();
    private static HashMap<Long, Attachment> attachments = new HashMap<>();

    private static MemoryDatabase instance = null;

    private MemoryDatabase() {
    }

    public static MemoryDatabase getInstance() {
        if (instance == null) {
            instance = new MemoryDatabase();
        }

        return instance;
    }

    @Override
    public void saveMessage(Message message) {
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
        Message message = messages.get(id);

        if (message == null) {
            throw new MessageNotFoundException();
        }

        message.setAttachments(getAttachmentsByMessageId(id));

        return message;
    }

    @Override
    public void deleteMessageById(long id) {
        messages.remove(id);
    }

    @Override
    public List<Message> getMessages() {
        return new ArrayList<>(messages.values());
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
        for (Attachment attachment : attachments.values()) {
            if (attachment.getMessageId() == messageId) {
                attachmentsList.add(attachment);
            }
        }

        return attachmentsList;
    }

    @Override
    public void deleteAttachmentById(long id) {
        attachments.remove(id);
    }

    @Override
    public void deleteAttachmentsByMessageId(long messageId) {
        List<Long> rowIds = new ArrayList<>();

        for (Attachment attachment : attachments.values()) {
            if (attachment.getMessageId() == messageId) {
                rowIds.add(attachment.getId());
            }
        }

        for (long rowId : rowIds) {
            attachments.remove(rowId);
        }
    }
}
