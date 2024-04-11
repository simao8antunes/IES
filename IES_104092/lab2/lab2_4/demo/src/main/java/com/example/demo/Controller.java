package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final String quotefile = "quotes.txt";

    @GetMapping("/api/quote")
    public Quote randomQuote() {
        ArrayList<Quote> quotes = loadQuotes();
        return quotes.get(randInt(quotes.size()));
    }

    @GetMapping("/api/shows")
    public HashSet<Show> getShows() {
        ArrayList<Quote> quotes = loadQuotes();
        HashSet<Show> shows = new HashSet<>();
        for (Quote q : quotes) 
            if(!shows.contains(q.getShow())) 
                shows.add(q.getShow());
        return shows;
    }

    @GetMapping("/api/quotes")
    public Quote randomQuoteFromShow(@RequestParam(name = "show", defaultValue = "1") long showid) {
        ArrayList<Quote> quotes = loadQuotes();
        ArrayList<Quote> filteredQuotes = new ArrayList<>();
        for(Quote q : quotes)
            if (q.getShow().getId() == showid)
                filteredQuotes.add(q);
        return filteredQuotes.get(randInt(filteredQuotes.size()));

    }

    private ArrayList<Quote> loadQuotes() {
        ArrayList<Quote> quotes = new ArrayList<>();
        Set<Show> shows = new HashSet<>();

        AtomicLong counter = new AtomicLong();

        Scanner filesc = null;
        try {
            filesc = new Scanner(new File(quotefile));

            while (filesc.hasNextLine()) {
                String[] data = filesc.nextLine().split(";");
                Show show = null;
                
                for(Show s : shows) {
                    if(s.getName().matches(data[1])) {
                        show = s;
                        break;
                    }
                }

                if(show == null) {
                    show = new Show(counter.incrementAndGet(), data[1]);
                    shows.add(show);
                }

                Quote quote = new Quote(data[0], show);
                quotes.add(quote);
            }

            filesc.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: Quote file not found.");
            System.exit(1);
        }

        return quotes;
    }

    private int randInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }
}