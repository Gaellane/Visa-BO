package com.projet.visa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.visa.model.VisaType;
import com.projet.visa.repository.VisaTypeRepository;

@Service
@Transactional(readOnly = true)
public class VisaTypeService {

    private final VisaTypeRepository visaTypeRepository;

    public VisaTypeService(VisaTypeRepository visaTypeRepository) {
        this.visaTypeRepository = visaTypeRepository;
    }

    public List<VisaType> findAll() {
        return visaTypeRepository.findAll();
    }
}