package com.projet.visa.repository;

import com.projet.visa.model.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DemandeRepository extends JpaRepository<Demande, Integer> {
    public List<Demande> findAll();
    public Optional<Demande> findById(Integer id);
}