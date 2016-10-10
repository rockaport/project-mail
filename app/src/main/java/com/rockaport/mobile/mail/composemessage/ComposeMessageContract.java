package com.rockaport.mobile.mail.composemessage;

import android.os.ParcelFileDescriptor;

class ComposeMessageContract {
    interface View {
        void showMessage(String message);

        void displayFilePicker();

        void close();
    }

    interface Presenter {
        void saveMessage(String message);

        void loadMessage(long id);

        void sendMessage();

        void deleteMessage();

        void getFile();

        void attachFile(String fileName, ParcelFileDescriptor file);
    }
}
