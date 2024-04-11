package com.example.demo;

public class Quote{

    private final String quote;
    private final Show show;

    public Quote(String quote, Show show){
        this.quote = quote;
        this.show = show;
    }

    public Show getShow(){
        return show;
    }

    public String getQuote(){
        return quote;
    }
}
