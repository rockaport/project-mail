package com.example.andrew.androidtesting.composemessage;

import android.util.Log;

import com.example.andrew.androidtesting.Injection;
import com.example.andrew.androidtesting.database.DatabaseApi;
import com.example.andrew.androidtesting.message.Message;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;

class ComposeMessagePresenter implements ComposeMessageContract.Presenter {
    private static final String TAG = "ComposeMessagePresenter";
    private ComposeMessageContract.View view;
    private DatabaseApi databaseApi;

    ComposeMessagePresenter(ComposeMessageContract.View view) {
        this.view = view;
        databaseApi = Injection.provideDatabase();
    }

    @Override
    public void saveMessage(Message message) {
        databaseApi.saveMessage(message);
    }

    @Override
    public void loadMessage(long id) {
        Observable.fromCallable(() -> databaseApi.getMessageById(id))
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
                        view.showMessage(message);
                    }
                });
    }
}
