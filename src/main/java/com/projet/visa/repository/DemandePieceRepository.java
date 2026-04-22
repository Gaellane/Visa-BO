package com.projet.visa.repository;

import com.projet.visa.model.DemandePiece;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DemandePieceRepository extends JpaRepository<DemandePiece, Integer> {
    List<DemandePiece> findByDemandeId(Integer demandeId);

}