package com.projet.visa.repository;

import com.projet.visa.model.Passeport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasseportRepository extends JpaRepository<Passeport, Integer> {
}