package com.projet.visa.repository;

import com.projet.visa.model.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeRepository extends JpaRepository<Demande, Integer> {

}