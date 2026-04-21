package com.projet.visa.repository;

import com.projet.visa.model.Nationalite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NationaliteRepository extends JpaRepository<Nationalite, Integer> {
    public List<Nationalite> findAll();
    public Nationalite findById();
}