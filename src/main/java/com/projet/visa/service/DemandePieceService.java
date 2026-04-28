package com.projet.visa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.projet.visa.model.Demande;
import com.projet.visa.model.DemandePiece;
import com.projet.visa.model.Demandeur;
import com.projet.visa.repository.DemandePieceRepository;
import com.projet.visa.util.UploadFile;

@Service
@Transactional(readOnly = true)
public class DemandePieceService {

    private final DemandePieceRepository demandePieceRepository;

    @Value("${upload.dir}")
    private String UPLOAD_DIR = "/uploads";

    public DemandePieceService(DemandePieceRepository demandePieceRepository) {
        this.demandePieceRepository = demandePieceRepository;
    }

    public List<DemandePiece> findByDemandeId(Integer demandeId) {
        return demandePieceRepository.findByDemandeId(demandeId);
    }

   
    
}