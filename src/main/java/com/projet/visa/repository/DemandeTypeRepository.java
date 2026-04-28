package com.projet.visa.repository;

import com.projet.visa.model.DemandeType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeTypeRepository extends JpaRepository<DemandeType, Integer> {
    List<DemandeType> findByIdIn(List<Integer> ids);
}