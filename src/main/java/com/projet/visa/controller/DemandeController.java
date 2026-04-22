package com.projet.visa.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projet.visa.model.PieceJustificative;
import com.projet.visa.service.DemandeService;
import com.projet.visa.service.DemandeurService;

@Controller
@RequestMapping("/demandes")
public class DemandeController {

    private final DemandeService demandeService;
    private final DemandeurService demandeurService;

    public DemandeController(DemandeService demandeService, DemandeurService demandeurService) {
        this.demandeService = demandeService;
        this.demandeurService = demandeurService;
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
}