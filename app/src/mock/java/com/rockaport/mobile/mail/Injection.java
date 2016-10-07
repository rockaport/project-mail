package com.rockaport.mobile.mail;

import com.rockaport.mobile.mail.database.DatabaseApi;
import com.rockaport.mobile.mail.database.MemoryDatabase;
import com.rockaport.mobile.mail.transport.MockTransport;
import com.rockaport.mobile.mail.transport.TransportApi;

public class Injection {
    public static DatabaseApi provideDatabase() {
        return MemoryDatabase.getInstance();
    }

    public static TransportApi provideTransport() {
        return MockTransport.getInstance();
    }
}
