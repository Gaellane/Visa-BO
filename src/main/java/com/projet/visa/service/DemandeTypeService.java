package com.projet.visa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.visa.model.DemandeType;
import com.projet.visa.repository.DemandeTypeRepository;

@Service
@Transactional(readOnly = true)
public class DemandeTypeService {

    private final DemandeTypeRepository demandeTypeRepository;

    public DemandeTypeService(DemandeTypeRepository demandeTypeRepository) {
        this.demandeTypeRepository = demandeTypeRepository;
    }

    public List<DemandeType> findAll() {
        return demandeTypeRepository.findAll();
    }
}