package com.projet.visa.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import com.projet.visa.service.DemandeurService;
import com.projet.visa.service.PasseportService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projet.visa.model.Demandeur;

@Controller
@RequestMapping("/demandeurs")
public class DemandeurController {

    private final DemandeurService demandeurService;
    private final PasseportService passeportService;

    public DemandeurController(DemandeurService demandeurService, PasseportService passeportService) {
        this.demandeurService = demandeurService;
        this.passeportService=passeportService;
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("demandeurs", demandeurService.findAll());
        return "demandeur/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("demandeur", demandeurService.findById(id));
        model.addAttribute("passeports", passeportService.findByDemandeur(id));
        return "demandeur/detail";
    }  

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("demandeur", new Demandeur());
        model.addAttribute("genres", demandeurService.findAllGenres());
        model.addAttribute("statusMaritals", demandeurService.findAllStatusMaritals());
        model.addAttribute("nationalites", demandeurService.findAllNationalites());
        return "demandeur/form";
    }

    @PostMapping({"", "/"})
    public String create(
            @RequestParam String nom,
            @RequestParam(required = false) String prenom,
            @RequestParam(required = false) String nomJeuneFille,
            @RequestParam String adresse,
            @RequestParam(required = false) String mail,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateNaissance,
            @RequestParam String tel,
            @RequestParam Integer genreId,
            @RequestParam Integer statusMaritalId,
            @RequestParam Integer nationaliteId,
            RedirectAttributes redirectAttributes) {

        try {
            demandeurService.create(
                    nom,
                    prenom,
                    nomJeuneFille,
                    adresse,
                    mail,
                    dateNaissance,
                    tel,
                    genreId,
                    statusMaritalId,
                    nationaliteId);
            redirectAttributes.addFlashAttribute("success", "Demandeur enregistré avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "demandeur/form";
        }

        return "redirect:/demandeurs";
    }
}