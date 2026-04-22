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
import com.projet.visa.model.Visa;
import com.projet.visa.model.VisaType;
import com.projet.visa.model.DemandePiece;
import com.projet.visa.model.Passeport;

import com.projet.visa.repository.DemandeRepository;
import com.projet.visa.repository.DemandeTypeRepository;
import com.projet.visa.repository.DemandePieceRepository;
import com.projet.visa.repository.PasseportRepository;
import com.projet.visa.repository.VisaTypeRepository;


@Controller
@RequestMapping("/demande")
public class DemandeController {

    private final DemandeRepository demandeRepository;
    private final DemandeTypeRepository demandeTypeRepository;
    private final VisaTypeRepository visaTypeRepository;
    private final DemandePieceRepository demandePieceRepository;
    private final PasseportRepository passeportRepository;

    public DemandeController(DemandeRepository demandeRepository, DemandeTypeRepository demandeTypeRepository, VisaTypeRepository visaTypeRepository, DemandePieceRepository demandePieceRepository, PasseportRepository passeportRepository ) {
        this.demandeRepository = demandeRepository;
        this.demandeTypeRepository = demandeTypeRepository;
        this.visaTypeRepository = visaTypeRepository;
        this.demandePieceRepository = demandePieceRepository;
        this.passeportRepository = passeportRepository;
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

        List<Demande> demandes = demandeRepository.search(dateMin, dateMax, typeId, visaTypeId);

        model.addAttribute("demandes", demandes);
        model.addAttribute("demandeTypes", demandeTypeRepository.findAll());
        model.addAttribute("visaTypes", visaTypeRepository.findAll());

        model.addAttribute("dateMin", dateMin);
        model.addAttribute("dateMax", dateMax);
        model.addAttribute("typeId", typeId);
        model.addAttribute("visaTypeId", visaTypeId);


        return "demande/liste";
    }

    @GetMapping("/fiche")
    public String getDetailsDemande(@RequestParam Integer id, Model model){
        Demande demande = demandeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Demande non trouvée avec l'id: " + id));
        model.addAttribute("demande", demande);

        Passeport passeport = null;
        if (demande.getDemandeur() != null) {
            passeport = passeportRepository.findByDemandeurId(demande.getDemandeur().getId()).orElse(null);
        }
        model.addAttribute("passeport", passeport);

        List<DemandePiece> pieces = demandePieceRepository.findByDemandeId(id);
        model.addAttribute("pieces", pieces);

        return "demande/fiche";
    }

}
