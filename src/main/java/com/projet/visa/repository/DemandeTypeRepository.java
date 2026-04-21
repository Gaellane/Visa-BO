package com.projet.visa.repository;

import com.projet.visa.model.DemandeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeTypeRepository extends JpaRepository<DemandeType, Integer> {
}