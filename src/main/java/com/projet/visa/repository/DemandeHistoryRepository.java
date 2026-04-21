package com.projet.visa.repository;

import com.projet.visa.model.DemandeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeHistoryRepository extends JpaRepository<DemandeHistory, Integer> {
}