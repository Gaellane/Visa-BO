package com.projet.visa.repository;

import com.projet.visa.model.Genre;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    public List<Genre> findAll();
    public Optional<Genre> findById(Integer id);
}