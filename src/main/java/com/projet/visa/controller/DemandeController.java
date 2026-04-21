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
import com.projet.visa.repository.DemandeRepository;
import com.projet.visa.repository.DemandeTypeRepository;
import com.projet.visa.repository.VisaTypeRepository;


@Controller
@RequestMapping("/demande")
public class DemandeController {

    private final DemandeRepository demandeRepository;
    private final DemandeTypeRepository demandeTypeRepository;
    private final VisaTypeRepository visaTypeRepository;

    public DemandeController(DemandeRepository demandeRepository, DemandeTypeRepository demandeTypeRepository, VisaTypeRepository visaTypeRepository) {
        this.demandeRepository = demandeRepository;
        this.demandeTypeRepository = demandeTypeRepository;
        this.visaTypeRepository = visaTypeRepository;
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

}
