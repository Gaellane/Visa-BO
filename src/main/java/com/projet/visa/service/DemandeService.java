package com.projet.visa.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.visa.model.Demande;
import com.projet.visa.model.DemandeHistory;
import com.projet.visa.model.DemandePiece;
import com.projet.visa.model.DemandeStatus;
import com.projet.visa.model.DemandeType;
import com.projet.visa.model.Demandeur;
import com.projet.visa.model.PieceJustificative;
import com.projet.visa.model.VisaType;
import com.projet.visa.repository.DemandeHistoryRepository;
import com.projet.visa.repository.DemandePieceRepository;
import com.projet.visa.repository.DemandeRepository;
import com.projet.visa.repository.DemandeStatusRepository;
import com.projet.visa.repository.DemandeTypeRepository;
import com.projet.visa.repository.DemandeurRepository;
import com.projet.visa.repository.PieceJustificativeRepository;
import com.projet.visa.repository.VisaTypeRepository;


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

        Map<PieceJustificative,Boolean> pieces = resolvePieces(visaTypeId, selectedPieces);
        validateMandatoryPieces(pieces);

        demandeRepository.save(demande);

        saveSelectedPieces(demande, pieces);

        DemandeStatus status = demandeStatusRepository.findById(STATUS_CREATE_ID).orElseThrow(()-> new IllegalArgumentException("Status introuvable " +STATUS_CREATE_ID));
        DemandeHistory history = new DemandeHistory();
        history.setDateChangement(now);
        history.setMotif("Creation du dossier de la demande "+demande.getId());
        history.setStatus(status);
        history.setDemande(demande);
        demandeHistoryRepository.save(history);

        return demande;
    
    }

    @Transactional
    public Demande update(
        Integer id,
        LocalDate date,
        Integer visaTypeId,
        Integer demandeTypeId,
        List<Integer> selectedPieces
    ) throws Exception {

        Demande demande = demandeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Demande non trouvée avec l'id: " + id));

        VisaType visaType = visaTypeRepository.findById(visaTypeId)
            .orElseThrow(() -> new IllegalArgumentException("Type visa introuvable: " + visaTypeId));
        DemandeType demandeType = demandeTypeRepository.findById(demandeTypeId)
            .orElseThrow(() -> new IllegalArgumentException("Type demande introuvable: " + demandeTypeId));

        if(date==null) {
            date = LocalDateTime.now().toLocalDate();
        }

        Map<PieceJustificative,Boolean> pieces = resolvePieces(visaTypeId, selectedPieces);
        validateMandatoryPieces(pieces);

        demande.setDateDemande(date);
        demande.setType(demandeType);
        demande.setTypeVisa(visaType);
        demandeRepository.save(demande);

        demandePieceRepository.deleteByDemandeId(id);
        saveSelectedPieces(demande, pieces);

        return demande;
    }

    private Map<PieceJustificative, Boolean> resolvePieces(Integer visaTypeId, List<Integer> selectedPieces) throws Exception {
        List<Integer> sanitizedSelectedPieces = selectedPieces == null ? List.of() : selectedPieces;
        Map<Integer,Boolean> piecesJustificatives = new HashMap<>();
        List<Integer> allPieces = pieceJustificativeRepository.findIdsByTypeVisa_IdIsNullOrTypeVisa_Id(visaTypeId);

        for(Integer pieceId : sanitizedSelectedPieces) {
            if(!allPieces.contains(pieceId)) {
                throw new Exception("Une piece ne correspond pas a la piece recherchee");
            }
        }

        for(Integer pieceId : allPieces) {
            piecesJustificatives.put(pieceId, sanitizedSelectedPieces.contains(pieceId));
        }

        return piecesJustificatives.entrySet().stream()
            .collect(Collectors.toMap(
                entry -> pieceJustificativeRepository.findById(entry.getKey()).orElseThrow(
                    () -> new IllegalArgumentException("Piece justificative introuvable: " + entry.getKey())),
                Entry::getValue
            ));
    }

    private void validateMandatoryPieces(Map<PieceJustificative, Boolean> pieces) throws Exception {
        boolean checkPieces = pieces.entrySet().stream()
            .allMatch(entry -> !entry.getKey().getObligatoire() || entry.getValue());

        if(!checkPieces) {
            throw new Exception("Piece obligatoire manquante");
        }
    }

    private void saveSelectedPieces(Demande demande, Map<PieceJustificative, Boolean> pieces) {
        for (Entry<PieceJustificative , Boolean> piece : pieces.entrySet()) {
            if(piece.getValue()) {
                DemandePiece dp = new DemandePiece();
                dp.setPiece(piece.getKey());
                dp.setDemande(demande);
                demandePieceRepository.save(dp);
            }
        }
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
