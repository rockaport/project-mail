package com.rockaport.mobile.mail.viewmessage;

public class ViewMessageContract {
    interface View {
        void showMessage(String message);

        void showDateTime(String dateTime);
    }

    interface Presenter {
        void loadMessage(long messageId);

        void reply();
    }
}
