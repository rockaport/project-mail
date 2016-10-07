package com.rockaport.mobile.mail.message;

import java.util.ArrayList;
import java.util.List;

public class Message {
    // The sql id of this message
    private long id;
    // The message type
    private TYPE type;
    // The message status
    private STATUS status;
    // The unix time of this message
    private long dateTime;
    // The parent of this message
    private long parentId;
    // The message body
    private String message = "";
    // List of attachments
    private List<Attachment> attachments = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public boolean isNew() {
        return id <= 0;
    }

    public int getAttachmentCount() {
        return attachments.size();
    }

    public enum TYPE {
        OUTGOING,
        INCOMING
    }

    public enum STATUS {
        NONE,
        READ,
        UNREAD,
        DRAFT
    }
}
