package com.projet.visa.repository;

import com.projet.visa.model.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeurRepository extends JpaRepository<Demandeur, Integer> {
}