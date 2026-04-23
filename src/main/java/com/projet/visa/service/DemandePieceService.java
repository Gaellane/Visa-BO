package com.projet.visa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.visa.model.DemandePiece;
import com.projet.visa.repository.DemandePieceRepository;

@Service
@Transactional(readOnly = true)
public class DemandePieceService {

    private final DemandePieceRepository demandePieceRepository;

    public DemandePieceService(DemandePieceRepository demandePieceRepository) {
        this.demandePieceRepository = demandePieceRepository;
    }

    public List<DemandePiece> findByDemandeId(Integer demandeId) {
        return demandePieceRepository.findByDemandeId(demandeId);
    }

    
}