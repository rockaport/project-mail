package com.example.andrew.androidtesting.composemessage;

import com.example.andrew.androidtesting.message.Message;

public class ComposeMessageContract {
    interface View {
        void showMessage(Message message);
    }

    interface Presenter {
        void saveMessage(Message message);

        void loadMessage(long id);
    }
}
