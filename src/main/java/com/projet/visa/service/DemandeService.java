package com.projet.visa.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.visa.model.Demande;
import com.projet.visa.repository.DemandeRepository;

import java.time.LocalDateTime;

import com.projet.visa.model.DemandeHistory;
import com.projet.visa.model.DemandePiece;
import com.projet.visa.model.DemandeStatus;
import com.projet.visa.model.DemandeType;
import com.projet.visa.model.Demandeur;
import com.projet.visa.model.PieceJustificative;
import com.projet.visa.model.VisaType;
import com.projet.visa.repository.DemandeHistoryRepository;
import com.projet.visa.repository.DemandePieceRepository;
import com.projet.visa.repository.DemandeStatusRepository;
import com.projet.visa.repository.DemandeTypeRepository;
import com.projet.visa.repository.DemandeurRepository;
import com.projet.visa.repository.PieceJustificativeRepository;
import com.projet.visa.repository.VisaTypeRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


@Service
public class DemandeService {
    private final DemandeRepository demandeRepository;
    private final PieceJustificativeRepository pieceJustificativeRepository;
    private final DemandePieceRepository demandePieceRepository;
    private final DemandeurRepository demandeurRepository;
    private final VisaTypeRepository visaTypeRepository;
    private final DemandeTypeRepository demandeTypeRepository;
    private final DemandeStatusRepository demandeStatusRepository;
    private final DemandeHistoryRepository demandeHistoryRepository;

    private final Integer STATUS_CREATE_ID=1;

    public DemandeService(
            DemandeRepository demandeRepository,
            PieceJustificativeRepository pieceJustificativeRepository,
            DemandePieceRepository demandePieceRepository,
            DemandeurRepository demandeurRepository,
            VisaTypeRepository visaTypeRepository,
            DemandeTypeRepository demandeTypeRepository,
            DemandeStatusRepository demandeStatusRepository,
            DemandeHistoryRepository demandeHistoryRepository) {
        this.demandeRepository = demandeRepository;
        this.pieceJustificativeRepository = pieceJustificativeRepository;
        this.demandePieceRepository = demandePieceRepository;
        this.demandeurRepository = demandeurRepository;
        this.visaTypeRepository = visaTypeRepository;
        this.demandeTypeRepository = demandeTypeRepository;
        this.demandeStatusRepository = demandeStatusRepository;
        this.demandeHistoryRepository = demandeHistoryRepository;
    }

    @Transactional 
    public Demande create (
        LocalDate date,
        Integer demandeurId,
        Integer visaTypeId,
        Integer demandeTypeId,
        List<Integer> selectedPieces
    ) throws Exception {

        Map<Integer,Boolean> pieces_justificatives = new HashMap<>();
        List<Integer> allPieces = pieceJustificativeRepository.findIdsByTypeVisa_IdIsNullOrTypeVisa_Id(visaTypeId);
        for(Integer i : selectedPieces) {
            if(!allPieces.contains(i)) {
                throw new Exception("Une piece ne correspond pas a la piece recherchee");
            }
        }
        for(Integer i : allPieces) {
            if(selectedPieces.contains(i)) {
                pieces_justificatives.put(i,true);
            } else {
                pieces_justificatives.put(i,false);
            }
        }


        LocalDateTime now  = LocalDateTime.now();

        Demandeur demandeur = demandeurRepository.findById(demandeurId).orElseThrow(() -> new IllegalArgumentException("Demandeur introuvable: " + demandeurId));
        VisaType visaType = visaTypeRepository.findById(visaTypeId).orElseThrow(() -> new IllegalArgumentException("Type visa introuvable: " + visaTypeId));
        DemandeType demandeType = demandeTypeRepository.findById(demandeTypeId).orElseThrow(() -> new IllegalArgumentException("Type demande introuvable: " + demandeTypeId));

        Demande demande = new Demande();
        if(date==null) {
            date=now.toLocalDate();
        }
        demande.setDateDemande(date);
        demande.setDemandeur(demandeur);
        demande.setType(demandeType);
        demande.setTypeVisa(visaType);


        for(Entry<Integer,Boolean> pj : pieces_justificatives.entrySet()) {
            System.out.println("\n\n PJ "+pj.getKey()+" - "+pj.getValue());
        }
        
        Map<PieceJustificative,Boolean> pieces = pieces_justificatives.entrySet().stream()
            .collect(Collectors.toMap(
                entry -> pieceJustificativeRepository.findById(entry.getKey()).get(),                
                entry -> entry.getValue()
            ));

        boolean checkPieces = pieces.values().stream()
                                    .allMatch(valeur -> valeur != false);

        if(!checkPieces) {
            throw new Exception("Piece obligatoire manquante");
        }

        demandeRepository.save(demande);

        for (Entry<PieceJustificative , Boolean> piece : pieces.entrySet()) {
            DemandePiece dp=new DemandePiece();
            dp.setPiece(piece.getKey());
            dp.setDemande(demande);
            demandePieceRepository.save(dp);
        }

        DemandeStatus status = demandeStatusRepository.findById(STATUS_CREATE_ID).orElseThrow(()-> new IllegalArgumentException("Status introuvable " +STATUS_CREATE_ID));
        DemandeHistory history = new DemandeHistory();
        history.setDateChangement(now);
        history.setMotif("Creation du dossier de la demande "+demande.getId());
        history.setStatus(status);
        history.setDemande(demande);
        demandeHistoryRepository.save(history);

        return demande;
    
    }

    public List<VisaType> findAllVisaTypes() {
        return visaTypeRepository.findAll();
    }

    public List<DemandeType> findAllDemandeTypes() {
        return demandeTypeRepository.findAll();
    }

    public List<PieceJustificative> findAllPieces() {
        return pieceJustificativeRepository.findAll();
    }

    public List<PieceJustificative> findCommonPieces() {
        return pieceJustificativeRepository.findByTypeVisaIsNull();
    }

    public List<Demande> search(LocalDate dateMin, LocalDate dateMax, Integer typeId, Integer visaTypeId) {
        return demandeRepository.search(dateMin, dateMax, typeId, visaTypeId);
    }

    public Demande getById(Integer id) {
        return demandeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Demande non trouvée avec l'id: " + id));
    }
}
