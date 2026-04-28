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
public class UploadDemandePieceService {

    private final DemandePieceRepository demandePieceRepository;

    @Value("${upload.dir}")
    private String UPLOAD_DIR = "/uploads";

    public UploadDemandePieceService(DemandePieceRepository demandePieceRepository) {
        this.demandePieceRepository = demandePieceRepository;
    }

    @Transactional
    public DemandePiece createDemandePiece(DemandePiece dp , MultipartFile file) throws Exception{
        if(dp.isAlreadyScanned()) 
            throw new Exception("Piece justificative deja importee");
        Demande demande =dp.getDemande();
        Demandeur demandeur = demande.getDemandeur();
        long timestamp = System.currentTimeMillis();
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName=demandeur.getId()+"_"+demandeur.getNom()+"_"+
                        demande.getId()+"_"+dp.getPiece().getCode()+"_"+
                        demande.getDateDemande().toString()+"_"+dp.getPiece().getId()+"_"+timestamp+extension;

        String link = Paths.get(UPLOAD_DIR, "pieces", fileName).toString();
        String piece_dir = Paths.get("src/main/resources/static",UPLOAD_DIR, "pieces").toString();
        dp.setCheminPiece(link);
        demandePieceRepository.save(dp);
        UploadFile.upload(piece_dir , fileName , file);
        return dp;
    }

    @Transactional
    public List<DemandePiece> createDemandePieces(List<DemandePiece>dps , Map<Integer , MultipartFile> files) throws Exception {
        List<DemandePiece> retour = new ArrayList<>();
        for(DemandePiece dp : dps ) {
            retour.add(createDemandePiece(dp, files.get(dp.getPiece().getId())));
        }
        return retour;
    }

    
}