package com.rockaport.mobile.mail.messagelist;

import com.rockaport.mobile.mail.database.DatabaseApi;

import rx.Completable;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class MessageListPresenter implements MessageListContract.Presenter {
    private MessageListContract.View view;
    private DatabaseApi databaseApi;

    MessageListPresenter(MessageListContract.View view, DatabaseApi databaseApi) {
        if (view == null || databaseApi == null) {
            throw new RuntimeException("View or database is null");
        }

        this.view = view;
        this.databaseApi = databaseApi;
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
    public void deleteMessage(long messageId) {
        view.removeMessage(messageId);

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
