package com.projet.visa.repository;

import java.util.List;

import com.projet.visa.model.PieceJustificative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PieceJustificativeRepository extends JpaRepository<PieceJustificative, Integer> {
    @Query("select pj from PieceJustificative pj where pj.nomPiece=?1")
    public PieceJustificative findByNomPiece(String nom);

    List<PieceJustificative> findByTypeVisaIsNull();

    List<PieceJustificative> findByTypeVisa_Id(Integer typeVisaId);

    List<PieceJustificative> findByTypeVisa_IdIsNullOrTypeVisa_Id(Integer typeVisaId);

    @Query("select pj.id from PieceJustificative pj where pj.typeVisa.id=?1 or pj.typeVisa is null")
    List<Integer> findIdsByTypeVisa_IdIsNullOrTypeVisa_Id(Integer typeVisaId);
    

    
}