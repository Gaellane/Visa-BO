package com.projet.visa.repository;

import com.projet.visa.model.StatusMarital;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusMaritalRepository extends JpaRepository<StatusMarital, Integer> {
    public List<StatusMarital> findAll();
    public StatusMarital findById();
}