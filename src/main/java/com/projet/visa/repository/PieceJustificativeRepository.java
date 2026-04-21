package com.projet.visa.repository;

import com.projet.visa.model.PieceJustificative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PieceJustificativeRepository extends JpaRepository<PieceJustificative, Integer> {
    @Query("select pj from PieceJustificative pj where pj.nomPiece=?1")
    public PieceJustificative findByNomPiece(String nom);

    
}