
package com.example.digitalib;

import android.app.Application;

public class DigiLibApplication extends Application {

    private BookRepository bookRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        bookRepository = new BookRepository();
    }

    public BookRepository getRepository() {
        return bookRepository;
    }
}
