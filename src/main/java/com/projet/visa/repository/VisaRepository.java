package com.projet.visa.repository;

import com.projet.visa.model.Visa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisaRepository extends JpaRepository<Visa, Integer> {
}