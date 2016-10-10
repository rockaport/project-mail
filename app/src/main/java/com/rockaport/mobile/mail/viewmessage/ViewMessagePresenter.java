package com.rockaport.mobile.mail.viewmessage;

import com.rockaport.mobile.mail.database.DatabaseApi;

import rx.Observable;

public class ViewMessagePresenter implements ViewMessageContract.Presenter {
    private ViewMessageContract.View view;
    private DatabaseApi databaseApi;

    public ViewMessagePresenter(ViewMessageContract.View view, DatabaseApi databaseApi) {
        this.view = view;
        this.databaseApi = databaseApi;
    }

    @Override
    public void loadMessage(long messageId) {
        Observable.fromCallable(() -> databaseApi.getMessageById(messageId))
                .subscribe();
    }

    @Override
    public void reply() {

    }
}
