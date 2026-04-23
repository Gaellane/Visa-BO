package com.projet.visa.repository;

import java.time.LocalDate;
import java.util.List;

import com.projet.visa.model.VisaTransformable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VisaTransformableRepository extends JpaRepository<VisaTransformable, Integer> {
	List<VisaTransformable> findByPasseport_Demandeur_Id(Integer demandeurId);

	@Query("SELECT vt FROM VisaTransformable vt WHERE vt.passeport.demandeur.id = ?1 AND ?2 BETWEEN vt.dateEntree AND vt.expiration")
	List<VisaTransformable> findByDemandeurAndDateBetween(Integer demandeurId,LocalDate date);
}