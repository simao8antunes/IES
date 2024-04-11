package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShowService {
    @Autowired
    private ShowRepository repository;

    public Movie saveShow(Movie show) {
        return repository.save(show);
    }

    public List<Movie> saveShows(List<Movie> shows) {
        return repository.saveAll(shows);
    }

    public List<Movie> getShows() {
        return repository.findAll();
    }

    public Movie getShowById(int id) {
        Optional<Movie> optional_movie = repository.findById(id);
        return optional_movie.get();
    }

    public Movie getShowByName(String name) {
        Optional<Movie> optional_movie = repository.findByName(name);
        return optional_movie.get();
    }

    public String deleteShow(int id) {
        repository.deleteById(id);
        return "show removed !! " + id;
    }
}