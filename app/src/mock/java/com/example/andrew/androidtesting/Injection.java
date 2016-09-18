package com.example.andrew.androidtesting;

import com.example.andrew.androidtesting.database.DatabaseApi;
import com.example.andrew.androidtesting.database.MemoryDatabase;

public class Injection {
    public static DatabaseApi provideDatabase() {
        return new MemoryDatabase();
    }
}
