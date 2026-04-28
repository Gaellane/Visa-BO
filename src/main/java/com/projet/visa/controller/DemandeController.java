package com.projet.visa.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projet.visa.model.Demande;
import com.projet.visa.model.DemandePiece;
import com.projet.visa.model.Passeport;
import com.projet.visa.model.PieceJustificative;
import com.projet.visa.service.DemandePieceService;
import com.projet.visa.service.DemandeService;
import com.projet.visa.service.DemandeTypeService;
import com.projet.visa.service.PasseportService;
import com.projet.visa.service.VisaTransformableService;
import com.projet.visa.service.VisaTypeService;
import com.projet.visa.service.VisaTransformableService;
import com.projet.visa.dto.DemandeListDto;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projet.visa.service.DemandeurService;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/demandes")
public class DemandeController {

    private final DemandeurService demandeurService;
    private final DemandeService demandeService;
    private final DemandeTypeService demandeTypeService;
    private final VisaTypeService visaTypeService;
    private final DemandePieceService demandePieceService;
    private final PasseportService passeportService;
    private final VisaTransformableService visaTransformableService;

    public DemandeController(
            DemandeurService demandeurService,
            DemandeService demandeService,
            DemandeTypeService demandeTypeService,
            VisaTypeService visaTypeService,
            DemandePieceService demandePieceService,
            PasseportService passeportService,
            VisaTransformableService visaTransformableService) {
        this.demandeurService = demandeurService;
        this.demandeService = demandeService;
        this.demandeTypeService = demandeTypeService;
        this.visaTypeService = visaTypeService;
        this.demandePieceService = demandePieceService;
        this.passeportService = passeportService;
        this.visaTransformableService = visaTransformableService;
    }

    @GetMapping("/new")
    public String createForm(@RequestParam Integer demandeurId, Model model) {
        populateForm(model, demandeurService.findById(demandeurId), null, List.of());
        return "demande/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Demande demande = demandeService.getById(id);
        List<Integer> selectedPieces = demandePieceService.findByDemandeId(id)
            .stream()
            .map(piece -> piece.getPiece().getId())
            .collect(Collectors.toList());

        populateForm(model, demande.getDemandeur(), demande, selectedPieces);
        return "demande/form";
    }

    @PostMapping
    public String create(
            @RequestParam Integer demandeurId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDemande,
            @RequestParam Integer visaTypeId,
            @RequestParam Integer demandeTypeId,
            @RequestParam(required = false, name = "selectedPieces") List<Integer> selectedPieces,
            RedirectAttributes redirectAttributes) {

        try {
            demandeService.create(dateDemande, demandeurId, visaTypeId, demandeTypeId, selectedPieces);
            redirectAttributes.addFlashAttribute("success", "Demande enregistree avec succes");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/demandes/new?demandeurId=" + demandeurId;
        }
        return "redirect:/demandeurs/" + demandeurId;
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Integer id,
            @RequestParam Integer demandeurId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDemande,
            @RequestParam Integer visaTypeId,
            @RequestParam Integer demandeTypeId,
            @RequestParam(required = false, name = "selectedPieces") List<Integer> selectedPieces,
            RedirectAttributes redirectAttributes) {

        try {
            demandeService.update(id, dateDemande, visaTypeId, demandeTypeId, selectedPieces);
            redirectAttributes.addFlashAttribute("success", "Demande modifiee avec succes");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/demandes/" + id + "/edit";
        }
        return "redirect:/demandeurs/" + demandeurId;
    }

    @GetMapping
    public String getListeDemande(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMin,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMax,
            @RequestParam(required = false) Integer typeId,
            @RequestParam(required = false) Integer visaTypeId,
        Model model) {

        List<DemandeListDto> demandes = demandeService.search(dateMin, dateMax, typeId, visaTypeId);

        model.addAttribute("demandes", demandes);
        model.addAttribute("demandeTypes", demandeTypeService.findAll());
        model.addAttribute("visaTypes", visaTypeService.findAll());

        model.addAttribute("dateMin", dateMin);
        model.addAttribute("dateMax", dateMax);
        model.addAttribute("typeId", typeId);
        model.addAttribute("visaTypeId", visaTypeId);
        model.addAttribute("idModifiable", demandeService.idModifiable());


        return "demande/list";
    }

    @GetMapping("/details")
    public String getDetailsDemande(@RequestParam Integer id, Model model){
        DemandeListDto demande = demandeService.getDtoById(id);
        model.addAttribute("demande", demande);

        Passeport passeport = null;
        if (demande.getDemandeur() != null) {
            passeport = passeportService.findByDemandeurId(demande.getDemandeur().getId());
        }
        model.addAttribute("passeport", passeport);

        List<DemandePiece> pieces = demandePieceService.findByDemandeId(id);
        model.addAttribute("pieces", pieces);

        model.addAttribute("idModifiable", demandeService.idModifiable());
        return "demande/detail";
    }

    private void populateForm(Model model, com.projet.visa.model.Demandeur demandeur, Demande demande, List<Integer> selectedPiecesIds) {
        model.addAttribute("demandeur", demandeur);
        model.addAttribute("demande", demande);
        model.addAttribute("selectedPiecesIds", selectedPiecesIds == null ? List.of() : selectedPiecesIds);
        model.addAttribute("visaTypes", demandeService.findAllVisaTypes());
        model.addAttribute("demandeTypes", demandeService.findAllDemandeTypes());
    }

    @GetMapping("/empty")
    public String transfertEmptyForm(@RequestParam Integer demandeurId, Model model) {
        model.addAttribute("demandeur", demandeurService.findById(demandeurId));
        model.addAttribute("passeports", passeportService.findByDemandeur(demandeurId));
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("visaTypes", demandeService.findAllVisaTypes());
        model.addAttribute("typeDemandes", demandeService.findTransfertAndDuplicataTypes());
        return "demande/empty";
    }

    // @GetMapping("/empty")
    // public String duplicataEmptyForm(@RequestParam Integer demandeurId, Model model) {
    //     model.addAttribute("demandeur", demandeurService.findById(demandeurId));
    //     model.addAttribute("today", LocalDate.now());
    //     model.addAttribute("visaTypes", demandeService.findAllVisaTypes());
    //     return "demande/duplicataEmpty";
    // }

    @PostMapping("/empty")
    public String transfertEmpty(
            
            @RequestParam("demandeurId") Integer demandeurId,
            @RequestParam("typeDemande") Integer typeDemandeId,
            @RequestParam("visaTypeId") Integer visaTypeId,
            @RequestParam("passeportId") Integer passeportId,

            @RequestParam("dateDemande") String dateDemande,

            @RequestParam("dateObtention") String dateObtention,

            @RequestParam("dateExpiration") String dateExpiration,
            RedirectAttributes redirectAttributes
        
        ) {
        
        try {
            if (demandeurId == null || visaTypeId == null || passeportId == null || dateObtention == null || dateExpiration == null) {
                throw new IllegalArgumentException("Tous les champs sont requis.");
            }
            LocalDate dateDemandeParsed = null;
            if (dateDemande != null && !dateDemande.isEmpty()) {
                dateDemandeParsed = LocalDate.parse(dateDemande);
            } else {
                dateDemandeParsed = LocalDate.now();
            }
            LocalDate dateObtentionParsed = LocalDate.parse(dateObtention); 
            LocalDate dateExpirationParsed = LocalDate.parse(dateExpiration);
            demandeService.transfertEmpty(demandeurId, typeDemandeId, dateDemandeParsed, visaTypeId, dateObtentionParsed, dateExpirationParsed);
            redirectAttributes.addFlashAttribute("success", "Demande Sans donnee anterieur cree avec succes");
            
            return "redirect:/demandes" ;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/demandes/transfertempty?demandeurId=" + demandeurId;
        }

    }


    // @PostMapping("/newTransfert")
    // public String newTransfert(@RequestParam Integer demandeurId,
    //         @RequestParam String numero,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate delivrance,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiration,
    //         RedirectAttributes redirectAttributes,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEntree,
    //         @RequestParam String reference,
    //         @RequestParam String lieu,
    //         @RequestParam Integer visaTypeId,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDemande,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expirationVisaTransf
    //     ) {

    //     try {
    //         List<Integer> pieces = new java.util.ArrayList<>();
    //         demandeService.createTransfert(dateDemande, demandeurId, visaTypeId, 3, pieces);
    //         redirectAttributes.addFlashAttribute("success", "Demande de transfert enregistree avec succes");

    //         passeportService.create(numero, delivrance, expiration, demandeurId);
    //         redirectAttributes.addFlashAttribute("success", "Passeport enregistre avec succès");

    //         Integer passeportId = passeportService.findByDemandeurId(demandeurId).getId();

    //         visaTransformableService.create(dateEntree, reference, lieu, expirationVisaTransf, passeportId);
    //         redirectAttributes.addFlashAttribute("success", "Visa transformable enregistre avec succes");

           
    //         return "redirect:/demandeurs/" + demandeurId;
    //     } catch (Exception e) {
    //         redirectAttributes.addFlashAttribute("error", e.getMessage());
    //         return "redirect:/demandes/transfert?demandeurId=" + demandeurId;
    //     }
        
    // }
    
}   



