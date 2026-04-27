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
import com.projet.visa.service.VisaTransformableService;

@Controller
@RequestMapping("/demandeurs")
public class DemandeurController {

    private final DemandeurService demandeurService;
    private final PasseportService passeportService;
    private final VisaTransformableService visaTransformableService;

    public DemandeurController(
            DemandeurService demandeurService,
            PasseportService passeportService,
            VisaTransformableService visaTransformableService) {
        this.demandeurService = demandeurService;
        this.passeportService = passeportService;
        this.visaTransformableService = visaTransformableService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("demandeurs", demandeurService.findAll());
        return "demandeur/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("demandeur", demandeurService.findById(id));
        model.addAttribute("passeports", passeportService.findByDemandeur(id));
        model.addAttribute("visaTransformables", visaTransformableService.findByDemandeur(id));
        model.addAttribute("canDemande" , demandeurService.canCreateDemande(id));
        model.addAttribute("canCreateEmpty" , demandeurService.canCreateEmpty(id));

        return "demandeur/detail";
    }  

    @GetMapping("/new")
    public String createForm(Model model) {
        populateForm(model, new Demandeur());
        return "demandeur/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        populateForm(model, demandeurService.findById(id));
        return "demandeur/form";
    }

    @PostMapping
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
            return "redirect:/demandeurs/new";
        }

        return "redirect:/demandeurs";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Integer id,
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
            demandeurService.update(
                    id,
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
            redirectAttributes.addFlashAttribute("success", "Demandeur modifie avec succes");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/demandeurs/" + id + "/edit";
        }

        return "redirect:/demandeurs/" + id;
    }

    private void populateForm(Model model, Demandeur demandeur) {
        model.addAttribute("demandeur", demandeur);
        model.addAttribute("genres", demandeurService.findAllGenres());
        model.addAttribute("statusMaritals", demandeurService.findAllStatusMaritals());
        model.addAttribute("nationalites", demandeurService.findAllNationalites());
    }
}