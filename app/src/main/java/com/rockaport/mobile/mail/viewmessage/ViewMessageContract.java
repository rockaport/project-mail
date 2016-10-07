package com.rockaport.mobile.mail.viewmessage;

public class ViewMessageContract {
    interface View {
        void showMessage(String message);
    }

    interface Presenter {
        void loadMessage(long messageId);
    }
}
