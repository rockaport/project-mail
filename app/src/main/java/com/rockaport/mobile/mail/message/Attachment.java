package com.rockaport.mobile.mail.message;

public class Attachment {
    // The sql id of this attachment
    private long id;
    // The sql id of the message
    private long messageId;
    // The size of this attachment
    private long size;
    // The path where the attachment is located
    private String path;
    // The file name // TODO: might remove and just use path
    private String fileName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isNew() {
        return id <= 0;
    }
}
