package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShowController {

    @Autowired
    private ShowService service;

    @PostMapping("/addshow")
    public ResponseEntity<Movie> addshow(@RequestBody Movie show) {
        Movie saved_movie = service.saveShow(show);
        return new ResponseEntity<>(saved_movie, HttpStatus.CREATED);
    }

    @GetMapping("/shows")
    public ResponseEntity<List<Movie>> findAllShows() {
        List<Movie> movies = service.getShows();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/showById/{id}")
    public ResponseEntity<Movie> findShowById(@PathVariable("id") int id) {
        Movie show = service.getShowById(id);
        return new ResponseEntity<>(show, HttpStatus.OK);
    }

    @GetMapping("/show/{name}")
    public ResponseEntity<Movie> findshowByName(@PathVariable String name) {
        Movie show = service.getShowByName(name);
        return new ResponseEntity<>(show, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteshow(@PathVariable("id") int id) {
        service.deleteShow(id);
        return new ResponseEntity<>("Show successfully deleted!", HttpStatus.OK);
    }
}