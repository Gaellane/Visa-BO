package com.projet.visa.repository;

import java.time.LocalDate;
import java.util.List;

import com.projet.visa.model.Passeport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PasseportRepository extends JpaRepository<Passeport, Integer> {
	Optional<Passeport> findByDemandeurId(Integer demandeurId);
	List<Passeport> findByDemandeur_Id(Integer demandeurId);

	@Query("SELECT p FROM Passeport p WHERE p.demandeur.id = ?1 AND ?2 BETWEEN p.delivrance AND p.expiration")
	List<Passeport> findByDemandeurIdAndDateBetween(Integer demandeurId, LocalDate date);
}