package com.rockaport.mobile.mail.database;

import com.rockaport.mobile.mail.message.Attachment;
import com.rockaport.mobile.mail.message.Message;

import java.util.List;

public interface DatabaseApi {
    // Message API
    void saveMessage(Message message);

    Message getMessageById(long id) throws MessageNotFoundException;

    void deleteMessageById(long id);

    List<Message> getMessages();

    // Attachment API
    void saveAttachment(Attachment attachment);

    Attachment getAttachmentById(long id) throws AttachmentNotFoundException;

    List<Attachment> getAttachmentsByMessageId(long messageId) throws AttachmentNotFoundException;

    void deleteAttachmentById(long id);

    void deleteAttachmentsByMessageId(long messageId);
}
