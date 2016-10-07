package com.rockaport.mobile.mail.composemessage;

import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.rockaport.mobile.mail.Injection;
import com.rockaport.mobile.mail.database.DatabaseApi;
import com.rockaport.mobile.mail.message.Attachment;
import com.rockaport.mobile.mail.message.Message;
import com.rockaport.mobile.mail.transport.TransportApi;

import rx.Completable;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

class ComposeMessagePresenter implements ComposeMessageContract.Presenter {
    private static final String TAG = "ComposeMessagePresenter";
    private ComposeMessageContract.View view;
    private DatabaseApi databaseApi = Injection.provideDatabase();
    private TransportApi transportApi = Injection.provideTransport();
    private Message message;

    ComposeMessagePresenter(ComposeMessageContract.View view) {
        this.view = view;
    }

    @Override
    public void saveMessage(String messageText) {
        message.setDateTime(System.currentTimeMillis());
        message.setMessage(messageText);

        Completable.fromAction(() -> databaseApi.saveMessage(message)).toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void loadMessage(long id) {
        Observable.fromCallable(() -> databaseApi.getMessageById(id))
                .onErrorReturn(throwable -> {
                    Message message = new Message();

                    message.setType(Message.TYPE.OUTGOING);
                    message.setStatus(Message.STATUS.DRAFT);
                    message.setDateTime(System.currentTimeMillis());

                    return message;
                })
                .subscribe(new Subscriber<Message>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(Message message) {
                        ComposeMessagePresenter.this.message = message;

                        view.showMessage(message.getMessage());
                    }
                });
    }

    @Override
    public void sendMessage() {
        transportApi.send(new Message());
    }

    @Override
    public void deleteMessage() {
        databaseApi.deleteMessageById(message.getId());
    }

    @Override
    public void getFile() {
        view.displayFilePicker();
    }

    @Override
    public void attachFile(String fileName, ParcelFileDescriptor file) {
        Attachment attachment = new Attachment();

        attachment.setSize(file.getStatSize());
        attachment.setFileName(fileName);
        attachment.setPath(fileName);

        message.getAttachments().add(attachment);
    }
}
