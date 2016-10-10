package com.rockaport.mobile.mail.messagelist;

import com.rockaport.mobile.mail.database.DatabaseApi;
import com.rockaport.mobile.mail.transport.TransportApi;

import rx.Completable;
import rx.Observable;
import rx.schedulers.Schedulers;

class MessageListPresenter implements MessageListContract.Presenter {
    private MessageListContract.View view;
    private DatabaseApi databaseApi;
    private TransportApi transportApi;

    MessageListPresenter(MessageListContract.View view, DatabaseApi databaseApi, TransportApi transportApi) {
        if (view == null || databaseApi == null || transportApi == null) {
            throw new RuntimeException("View, database, or transport is null");
        }

        this.view = view;
        this.databaseApi = databaseApi;
        this.transportApi = transportApi;
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
        Observable.concat(Observable.fromCallable(() -> transportApi.receive())
                        .flatMapIterable(messages -> messages)
                        .flatMap(message -> Completable.fromAction(() -> databaseApi.saveMessage(message)).toObservable()),
                Observable.fromCallable(() -> databaseApi.getMessages()))
                .doOnTerminate(() -> view.showLoadingSpinner(false))
                .subscribe(messages -> {
                    view.showMessages(messages);
                });
    }
}
