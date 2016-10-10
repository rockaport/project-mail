package com.rockaport.mobile.mail.transport;

import android.util.Log;

import com.rockaport.mobile.mail.models.message.Message;
import com.rockaport.mobile.mail.models.message.MessageUtil;

import java.util.List;
import java.util.Random;

public class MockTransport implements TransportApi {
    private static final String TAG = "MockTransport";
    private static MockTransport instance;

    public static MockTransport getInstance() {
        if (instance == null) {
            instance = new MockTransport();
        }

        return instance;
    }

    @Override
    public void send(Message message) {
        Log.d(TAG, "send() called with: message = [" + message + "]");
        // no op
    }

    @Override
    public List<Message> receive() {
        Log.d(TAG, "receive() called");
        return MessageUtil.generateRandomMessages(new Random().nextInt(3) + 1);
    }
}
