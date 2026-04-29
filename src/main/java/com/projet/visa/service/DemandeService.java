package com.projet.visa.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.projet.visa.util.ReferenceGenerator;
import com.projet.visa.model.CarteResident;
import com.projet.visa.model.Demande;
import com.projet.visa.model.DemandeHistory;
import com.projet.visa.model.DemandePiece;
import com.projet.visa.model.DemandeStatus;
import com.projet.visa.model.DemandeType;
import com.projet.visa.model.Demandeur;
import com.projet.visa.model.PieceJustificative;
import com.projet.visa.model.Visa;
import com.projet.visa.model.VisaType;
import com.projet.visa.repository.DemandeHistoryRepository;
import com.projet.visa.repository.DemandePieceRepository;
import com.projet.visa.repository.DemandeRepository;
import com.projet.visa.repository.DemandeStatusRepository;
import com.projet.visa.repository.DemandeTypeRepository;
import com.projet.visa.repository.DemandeurRepository;
import com.projet.visa.repository.PasseportRepository;
import com.projet.visa.repository.PieceJustificativeRepository;
import com.projet.visa.repository.VisaTransformableRepository;
import com.projet.visa.repository.VisaTypeRepository;
import com.projet.visa.repository.VisaRepository;
import com.projet.visa.repository.CarteResidentRepository;
import com.projet.visa.dto.DemandeListDto;


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
    private final VisaTransformableRepository visaTransformableRepository;
    private final PasseportRepository passeportRepository;
    private final VisaRepository visaRepository;
    private final CarteResidentRepository carteResidentRepository;
    private final UploadDemandePieceService uploadDemandePieceService;
    


    private Integer STATUS_CREATE_ID=1;
    private Integer STATUS_VALIDATION_ID=3;
    private Integer STATUS_SCAN_TEMNINE_ID=2;

    private Integer newDemandeTypeId=1;
    private Integer transfertTypeId=3;
    private Integer duplicataTypeId=2;

    public DemandeService(
            DemandeRepository demandeRepository,
            PieceJustificativeRepository pieceJustificativeRepository,
            DemandePieceRepository demandePieceRepository,
            DemandeurRepository demandeurRepository,
            VisaTypeRepository visaTypeRepository,
            DemandeTypeRepository demandeTypeRepository,
            DemandeStatusRepository demandeStatusRepository,
            DemandeHistoryRepository demandeHistoryRepository,
            VisaTransformableRepository visaTransformableRepository,
            PasseportRepository passeportRepository ,
            VisaRepository visaRepository,
            CarteResidentRepository carteResidentRepository,
            UploadDemandePieceService uploadDemandePieceService) {
        this.demandeRepository = demandeRepository;
        this.pieceJustificativeRepository = pieceJustificativeRepository;
        this.demandePieceRepository = demandePieceRepository;
        this.demandeurRepository = demandeurRepository;
        this.visaTypeRepository = visaTypeRepository;
        this.demandeTypeRepository = demandeTypeRepository;
        this.demandeStatusRepository = demandeStatusRepository;
        this.demandeHistoryRepository = demandeHistoryRepository;
        this.visaTransformableRepository = visaTransformableRepository;
        this.passeportRepository=passeportRepository;
        this.visaRepository=visaRepository;
        this.carteResidentRepository=carteResidentRepository;
        this.uploadDemandePieceService=uploadDemandePieceService;
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
        LocalDate today = now.toLocalDate();
        Demandeur demandeur = demandeurRepository.findById(demandeurId).orElseThrow(() -> new IllegalArgumentException("Demandeur introuvable: " + demandeurId));

        if(visaTransformableRepository.findByDemandeurAndDateBetween(demandeurId,today).size()==0) {
            throw new Exception("Ce demandeur n'as pas encore de visa transformable");
        }
        if(passeportRepository.findByDemandeurIdAndDateBetween(demandeurId,today).size()==0) 
            throw new Exception("Ce demandeur n'a pas encore de passeport");


        VisaType visaType = visaTypeRepository.findById(visaTypeId).orElseThrow(() -> new IllegalArgumentException("Type visa introuvable: " + visaTypeId));
        DemandeType demandeType = demandeTypeRepository.findById(demandeTypeId).orElseThrow(() -> new IllegalArgumentException("Type demande introuvable: " + demandeTypeId));
        String demandeNumero ="DM-"+ReferenceGenerator.generateReference();
        Demande demande = new Demande();
        if(date==null) {
            date=now.toLocalDate();
        }
        demande.setNumero(demandeNumero);
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

    public List<DemandeType> findTransfertAndDuplicataTypes() {
        return demandeTypeRepository.findByIdIn(List.of(transfertTypeId, duplicataTypeId));
    }

    public List<PieceJustificative> findAllPieces() {
        return pieceJustificativeRepository.findAll();
    }

    public List<PieceJustificative> findCommonPieces() {
        return pieceJustificativeRepository.findByTypeVisaIsNull();
    }

    public Integer idModifiable(){
        return STATUS_CREATE_ID;
    }

    public List<DemandeListDto> search(LocalDate dateMin, LocalDate dateMax, Integer typeId, Integer visaTypeId) {
        List<Demande> demandes = demandeRepository.search(dateMin, dateMax, typeId, visaTypeId);
        List<DemandeListDto> dtos = demandes.stream().map(demande -> new DemandeListDto(
            demande.getId(),
            demande.getDateDemande(),
            demande.getNumero(),
            demande.getDemandeur(),
            demande.getType(),
            demande.getTypeVisa(),
            demandeHistoryRepository.findByDemandeIdOrderByDateDesc(demande.getId()).stream().findFirst().map(DemandeHistory::getStatus).orElse(null)
        )).collect(Collectors.toList());

        return dtos;
    }

    public Demande getById(Integer id) {
        return demandeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Demande non trouvée avec l'id: " + id));
    }
    public DemandeListDto getDtoById(Integer id) {
        Demande demande = getById(id);
        DemandeStatus status = demandeHistoryRepository.findByDemandeIdOrderByDateDesc(demande.getId()).stream().findFirst().map(DemandeHistory::getStatus).orElse(null);
        return new DemandeListDto(
            demande.getId(),
            demande.getDateDemande(),
            demande.getNumero(),
            demande.getDemandeur(),
            demande.getType(),
            demande.getTypeVisa(),
            status
        );
    }

    public Demande newDemandeValidate(Demandeur demandeur, LocalDateTime now , LocalDate date, VisaType visaType ,String motif , LocalDate dateObtention, LocalDate dateExpiration) throws Exception {
        DemandeType demandeType = demandeTypeRepository.findById(newDemandeTypeId).orElseThrow(() -> new IllegalArgumentException("Type demande introuvable: " + newDemandeTypeId));
        DemandeStatus status = demandeStatusRepository.findById(STATUS_VALIDATION_ID).orElseThrow(()-> new IllegalArgumentException("Status introuvable " +STATUS_VALIDATION_ID));
        
        Demande demande = new Demande();
        demande.setDateDemande(date);
        demande.setNumero("DM-"+ReferenceGenerator.generateReference());
        demande.setDemandeur(demandeur);
        demande.setType(demandeType);
        demande.setTypeVisa(visaType);

        DemandeHistory history = new DemandeHistory();
        history.setDateChangement(now);
        history.setMotif(motif);
        history.setStatus(status);
        history.setDemande(demande);

        Visa visa = new Visa();
        visa.setDateEntree(dateObtention);
        visa.setDateExpiration(dateExpiration);
        visa.setDemande(demande);
        visa.setPasseport(passeportRepository.findByDemandeurIdAndDateBetween(demandeur.getId(),date).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Aucun passeport trouvé pour ce demandeur à cette date")));
        visa.setReference(ReferenceGenerator.generateReference());

        CarteResident carteResident = new CarteResident();
        carteResident.setDemande(demande);
        carteResident.setDateEntree(dateObtention);
        carteResident.setDateExpiration(dateExpiration);
        carteResident.setResident(demandeur);

        demandeRepository.save(demande);
        demandeHistoryRepository.save(history);
        visaRepository.save(visa);
        carteResidentRepository.save(carteResident);

        return demande;
    }

    public Demande transfertEmpty(Integer demandeurId, Integer typeDemandeId, LocalDate date, Integer visaTypeId, LocalDate dateObtention, LocalDate dateExpiration) throws Exception {
        Demandeur demandeur = demandeurRepository.findById(demandeurId).orElseThrow(() -> new IllegalArgumentException("Demandeur introuvable: " + demandeurId));
        VisaType visaType = visaTypeRepository.findById(visaTypeId).orElseThrow(() -> new IllegalArgumentException("Type visa introuvable: " + visaTypeId));
        DemandeType demandeType = demandeTypeRepository.findById(typeDemandeId).orElseThrow(() -> new IllegalArgumentException("Type demande introuvable: " + typeDemandeId));
        DemandeStatus status = demandeStatusRepository.findById(STATUS_CREATE_ID).orElseThrow(()-> new IllegalArgumentException("Status introuvable " +STATUS_CREATE_ID));
        
        LocalDateTime now  = LocalDateTime.now();
        if(date==null) {
            date=now.toLocalDate();
        }

        newDemandeValidate(demandeur,now , date , visaType , "Transfert avec données antérieures" , dateObtention, dateExpiration);

        Demande demande = new Demande();
        demande.setDateDemande(date);
        demande.setNumero("DM-"+ReferenceGenerator.generateReference());
        demande.setDemandeur(demandeur);
        demande.setType(demandeType);
        demande.setTypeVisa(visaType);

        DemandeHistory history = new DemandeHistory();
        history.setDateChangement(now);
        history.setMotif("Transfert sans données antérieures");
        history.setDemande(demande);
        history.setStatus(status);

       
        demandeRepository.save(demande);
        demandeHistoryRepository.save(history);

        return demande;
    }


    @Transactional
    public Demande scannerDemandePieces(Integer demandeId ,  Map<Integer , MultipartFile> files) throws Exception{
        LocalDateTime now = LocalDateTime.now();
        Demande demande = demandeRepository.findById(demandeId).orElseThrow(() -> new IllegalArgumentException("Demandeur introuvable: " + demandeId));
        List<DemandePiece> piecesSaved = demandePieceRepository.findByDemandeId(demandeId);
        if(files.size()<piecesSaved.size()) {
            throw new Exception("Pieces ulpoades insuffisants");
        }
        List<DemandePiece> dps= uploadDemandePieceService.createDemandePieces(piecesSaved, files);

        DemandeHistory history = new DemandeHistory();
        history.setDateChangement(now);
        history.setDemande(demande);
        history.setMotif("Scan termine");

        DemandeStatus status = demandeStatusRepository.findById(STATUS_SCAN_TEMNINE_ID).orElseThrow(() -> new IllegalArgumentException("Status scan termine introuvable"));
        history.setStatus(status);

        demandeHistoryRepository.save(history);

        return demande;
    }


}
