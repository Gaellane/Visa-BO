package com.projet.visa.repository;

import com.projet.visa.model.DemandeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeStatusRepository extends JpaRepository<DemandeStatus, Integer> {
}