package com.projet.visa.repository;

import com.projet.visa.model.Demande;
import com.projet.visa.model.DemandeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface DemandeHistoryRepository extends JpaRepository<DemandeHistory, Integer> {
    @Query("SELECT dh FROM DemandeHistory dh WHERE dh.demande.id = :demandeId")
    public DemandeHistory findByIdDemande(@Param("demandeId") Integer demandeId);

    @Query("SELECT dh FROM DemandeHistory dh WHERE dh.demande.id = :demandeId ORDER BY dh.dateChangement DESC")
    List<DemandeHistory> findByDemandeIdOrderByDateDesc(@Param("demandeId") Integer demandeId);
}