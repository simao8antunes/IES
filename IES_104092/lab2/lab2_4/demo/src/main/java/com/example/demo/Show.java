package com.example.demo;

import java.util.ArrayList;

public class Show {
    
    private final long id;
    private final String name;
    private ArrayList<Quote> quotes;

    public Show(long id, String name) {
        this.id = id;
        this.name = name;
        this.quotes = new ArrayList<>();
    }

    public void addQuote(Quote q) {
        quotes.add(q);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}