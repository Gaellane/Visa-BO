package com.projet.visa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.visa.model.Passeport;
import com.projet.visa.repository.PasseportRepository;

@Service
@Transactional(readOnly = true)
public class PasseportService {

    private final PasseportRepository passeportRepository;

    public PasseportService(PasseportRepository passeportRepository) {
        this.passeportRepository = passeportRepository;
    }

    public Passeport findByDemandeurId(Integer demandeurId) {
        return passeportRepository.findByDemandeurId(demandeurId).orElse(null);
    }
}