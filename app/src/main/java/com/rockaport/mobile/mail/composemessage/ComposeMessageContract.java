package com.rockaport.mobile.mail.composemessage;

import com.rockaport.mobile.mail.message.Message;

public class ComposeMessageContract {
    interface View {
        void showMessage(Message message);
    }

    interface Presenter {
        void saveMessage(Message message);

        void loadMessage(long id);
    }
}
