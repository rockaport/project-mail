package com.rockaport.mobile.mail.messagelist;

import com.rockaport.mobile.mail.Injection;
import com.rockaport.mobile.mail.database.DatabaseApi;

import rx.Completable;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MessageListPresenter implements MessageListContract.Presenter {
    private MessageListContract.View view;
    private DatabaseApi databaseApi;

    public MessageListPresenter(MessageListContract.View view) {
        this.view = view;
        databaseApi = Injection.provideDatabase();
    }

    @Override
    public void composeMessage() {
        view.showComposeMessage();
    }

    @Override
    public void openMessage(long messageId) {
        view.showMessage(messageId);
    }

    @Override
    public void deleteMessage(int position, long messageId) {
        view.removeMessage(position);

        Completable.fromAction(() -> databaseApi.deleteMessageById(messageId))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void loadMessages() {
        view.showLoadingSpinner(true);

        Observable.fromCallable(() -> databaseApi.getMessages())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> view.showLoadingSpinner(false))
                .subscribe(messages -> {
                    view.showMessages(messages);
                });
    }
}
