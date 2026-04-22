package com.projet.visa.repository;

import com.projet.visa.model.Passeport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PasseportRepository extends JpaRepository<Passeport, Integer> {
	Optional<Passeport> findByDemandeurId(Integer demandeurId);
}