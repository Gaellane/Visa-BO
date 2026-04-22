package com.projet.visa.repository;

import java.util.List;

import com.projet.visa.model.VisaTransformable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisaTransformableRepository extends JpaRepository<VisaTransformable, Integer> {
	List<VisaTransformable> findByPasseport_Demandeur_Id(Integer demandeurId);
}