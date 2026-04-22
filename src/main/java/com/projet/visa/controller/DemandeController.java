package com.projet.visa.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projet.visa.model.Demande;
import com.projet.visa.model.DemandePiece;
import com.projet.visa.model.Passeport;

import com.projet.visa.service.DemandePieceService;
import com.projet.visa.service.DemandeService;
import com.projet.visa.service.DemandeTypeService;
import com.projet.visa.service.PasseportService;
import com.projet.visa.service.VisaTypeService;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projet.visa.service.DemandeurService;

@Controller
@RequestMapping("/demandes")
public class DemandeController {

    private final DemandeurService demandeurService;
    private final DemandeService demandeService;
    private final DemandeTypeService demandeTypeService;
    private final VisaTypeService visaTypeService;
    private final DemandePieceService demandePieceService;
    private final PasseportService passeportService;

    public DemandeController(
            DemandeurService demandeurService,
            DemandeService demandeService,
            DemandeTypeService demandeTypeService,
            VisaTypeService visaTypeService,
            DemandePieceService demandePieceService,
            PasseportService passeportService) {
        this.demandeurService = demandeurService;
        this.demandeService = demandeService;
        this.demandeTypeService = demandeTypeService;
        this.visaTypeService = visaTypeService;
        this.demandePieceService = demandePieceService;
        this.passeportService = passeportService;
    }

    @GetMapping("/new")
    public String createForm(@RequestParam Integer demandeurId, Model model) {
        model.addAttribute("demandeur", demandeurService.findById(demandeurId));
        model.addAttribute("visaTypes", demandeService.findAllVisaTypes());
        model.addAttribute("demandeTypes", demandeService.findAllDemandeTypes());
        return "demande/form";
    }

    @PostMapping("")
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
            return "demande/form";
        }
        return "redirect:/demandeurs/" + demandeurId;
    }

    @GetMapping("/liste")
    public String getListeDemande(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMin,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMax,
            @RequestParam(required = false) Integer typeId,
            @RequestParam(required = false) Integer visaTypeId,
        Model model) {

        List<Demande> demandes = demandeService.search(dateMin, dateMax, typeId, visaTypeId);

        model.addAttribute("demandes", demandes);
        model.addAttribute("demandeTypes", demandeTypeService.findAll());
        model.addAttribute("visaTypes", visaTypeService.findAll());

        model.addAttribute("dateMin", dateMin);
        model.addAttribute("dateMax", dateMax);
        model.addAttribute("typeId", typeId);
        model.addAttribute("visaTypeId", visaTypeId);


        return "demande/liste";
    }

    @GetMapping("/fiche")
    public String getDetailsDemande(@RequestParam Integer id, Model model){
        Demande demande = demandeService.getById(id);
        model.addAttribute("demande", demande);

        Passeport passeport = null;
        if (demande.getDemandeur() != null) {
            passeport = passeportService.findByDemandeurId(demande.getDemandeur().getId());
        }
        model.addAttribute("passeport", passeport);

        List<DemandePiece> pieces = demandePieceService.findByDemandeId(id);
        model.addAttribute("pieces", pieces);

        return "demande/fiche";
    }
}
