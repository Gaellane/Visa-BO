package com.projet.visa.repository;

import com.projet.visa.model.Visa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface VisaRepository extends JpaRepository<Visa, Integer> {
    @Query("SELECT v FROM Visa v WHERE v.passeport.demandeur.id = ?1 AND ?2 BETWEEN v.dateEntree AND v.dateExpiration")
	List<Visa> findByDemandeurAndDateBetween(Integer demandeurId,LocalDate date);

}