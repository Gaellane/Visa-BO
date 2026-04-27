package com.projet.visa.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projet.visa.service.DemandeurService;
import com.projet.visa.service.PasseportService;

@Controller
@RequestMapping("/passeports")
public class PasseportController {

    private final PasseportService passeportService;
    private final DemandeurService demandeurService;

    public PasseportController(PasseportService passeportService, DemandeurService demandeurService) {
        this.passeportService = passeportService;
        this.demandeurService = demandeurService;
    }

    @GetMapping("/new")
    public String createForm(@RequestParam Integer demandeurId, Model model) {
        model.addAttribute("demandeur", demandeurService.findById(demandeurId));
        return "passeport/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        com.projet.visa.model.Passeport passeport = passeportService.findById(id);
        model.addAttribute("demandeur", passeport.getDemandeur());
        model.addAttribute("passeport", passeport);
        return "passeport/form";
    }

    @PostMapping("")
    public String create(
            @RequestParam Integer demandeurId,
            @RequestParam String numero,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate delivrance,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiration,
            RedirectAttributes redirectAttributes) {

        try {
            passeportService.create(numero, delivrance, expiration, demandeurId);
            redirectAttributes.addFlashAttribute("success", "Passeport enregistré avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "passeport/form";
        }
        return "redirect:/demandeurs/" + demandeurId;
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Integer id,
            @RequestParam String numero,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate delivrance,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiration,
            RedirectAttributes redirectAttributes) {

        com.projet.visa.model.Passeport passeport = passeportService.findById(id);
        Integer demandeurId = passeport.getDemandeur().getId();

        try {
            passeportService.update(id, numero, delivrance, expiration);
            redirectAttributes.addFlashAttribute("success", "Passeport modifie avec succes");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/passeports/" + id + "/edit";
        }
        return "redirect:/demandeurs/" + demandeurId;
    }
}