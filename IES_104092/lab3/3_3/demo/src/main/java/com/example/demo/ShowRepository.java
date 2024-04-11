package com.example.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Movie,Integer> {
    Optional<Movie> findByName(String name);
    Optional<Movie> findById(long id);
}