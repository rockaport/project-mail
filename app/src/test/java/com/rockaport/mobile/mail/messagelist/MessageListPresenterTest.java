package com.rockaport.mobile.mail.messagelist;

import com.rockaport.mobile.mail.Injection;
import com.rockaport.mobile.mail.message.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MessageListPresenterTest {
    private MessageListActivity view;
    private MessageListPresenter messageListPresenter;

    @Before
    public void setup() {
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });

        view = mock(MessageListActivity.class);
        messageListPresenter = new MessageListPresenter(view, Injection.provideDatabase());
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }

    @Test(expected = RuntimeException.class)
    public void exceptionWithNullView() {
        new MessageListPresenter(null, Injection.provideDatabase());
    }

    @Test(expected = RuntimeException.class)
    public void exceptionWithNullDatabase() {
        new MessageListPresenter(view, null);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionWithNullViewAndDatabase() {
        new MessageListPresenter(null, null);
    }

    @Test
    public void callComposeMessage() {
        messageListPresenter.composeMessage();

        verify(view).showComposeMessage();
    }

    @Test
    public void callOpenMessage() {
        long messageId = 0;

        messageListPresenter.openMessage(messageId);

        verify(view).showMessage(messageId);
    }

    @Test
    public void callDeleteMessage() {
        long messageId = 0;

        messageListPresenter.deleteMessage(messageId);

        verify(view).removeMessage(messageId);
    }

    @Test
    public void callLoadMessages() {
        messageListPresenter.loadMessages();

        verify(view).showLoadingSpinner(true);
        verify(view).showMessages(anyListOf(Message.class));
        verify(view).showLoadingSpinner(false);
    }
}
