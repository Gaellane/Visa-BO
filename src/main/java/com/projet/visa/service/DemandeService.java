package com.projet.visa.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.visa.model.Demande;
import com.projet.visa.repository.DemandeRepository;

@Service
@Transactional(readOnly = true)
public class DemandeService {

    private final DemandeRepository demandeRepository;

    public DemandeService(DemandeRepository demandeRepository) {
        this.demandeRepository = demandeRepository;
    }

    public List<Demande> search(LocalDate dateMin, LocalDate dateMax, Integer typeId, Integer visaTypeId) {
        return demandeRepository.search(dateMin, dateMax, typeId, visaTypeId);
    }

    public Demande getById(Integer id) {
        return demandeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Demande non trouvée avec l'id: " + id));
    }
}