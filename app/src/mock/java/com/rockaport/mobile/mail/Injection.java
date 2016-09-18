package com.rockaport.mobile.mail;

import com.rockaport.mobile.mail.database.DatabaseApi;
import com.rockaport.mobile.mail.database.MemoryDatabase;

public class Injection {
    public static DatabaseApi provideDatabase() {
        return new MemoryDatabase();
    }
}
