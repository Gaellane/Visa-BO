package com.projet.visa.repository;

import com.projet.visa.model.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DemandeRepository extends JpaRepository<Demande, Integer> {
    public List<Demande> findAll();
    public Optional<Demande> findById(Integer id);

    @Query("SELECT d FROM Demande d WHERE (cast(:dateMin as date) IS NULL OR d.dateDemande >= :dateMin) AND (cast(:dateMax as date) IS NULL OR d.dateDemande <= :dateMax) AND (cast(:typeId as integer) IS NULL OR d.type.id = :typeId) AND (cast(:visaTypeId as integer) IS NULL OR d.typeVisa.id = :visaTypeId)")
    List<Demande> search(
    @Param("dateMin") LocalDate dateMin,
    @Param("dateMax") LocalDate dateMax,
    @Param("typeId") Integer typeId,
    @Param("visaTypeId") Integer visaTypeId);
}