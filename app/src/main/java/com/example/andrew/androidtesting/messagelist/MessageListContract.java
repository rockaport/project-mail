package com.example.andrew.androidtesting.messagelist;

import com.example.andrew.androidtesting.message.Message;

import java.util.List;

public class MessageListContract {
    interface View {
        void removeMessage(int position);

        void showComposeMessage();

        void showMessage(long messageId);

        void showLoadingSpinner(boolean loading);

        void showMessages(List<Message> messages);
    }

    interface ListView {
        void showMessage(Message message);

        void showMessages(List<Message> messages);

        void removeMessage(int position);
    }

    interface Presenter {
        void composeMessage();

        void openMessage(long messageId);

        void deleteMessage(int position, long messageId);

        void loadMessages();
    }
}
