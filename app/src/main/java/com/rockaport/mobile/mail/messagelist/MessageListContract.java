package com.rockaport.mobile.mail.messagelist;

import com.rockaport.mobile.mail.models.message.Message;

import java.util.List;

public class MessageListContract {
    public interface View {
        void removeMessage(long messageId);

        void showComposeMessage();

        void showMessage(long messageId);

        void showLoadingSpinner(boolean loading);

        void showMessages(List<Message> messages);
    }

    public interface ListView {
        void showMessages(List<Message> messages);

        void removeMessage(long messageId);
    }

    public interface Presenter {
        void composeMessage();

        void openMessage(long messageId);

        void deleteMessage(long messageId);

        void loadMessages();
    }
}
