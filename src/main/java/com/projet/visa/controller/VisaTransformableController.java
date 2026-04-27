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

import com.projet.visa.model.Passeport;
import com.projet.visa.service.PasseportService;
import com.projet.visa.service.VisaTransformableService;

@Controller
@RequestMapping("/visa_transformables")
public class VisaTransformableController {

    private final VisaTransformableService visaTransformableService;
    private final PasseportService passeportService;

    public VisaTransformableController(VisaTransformableService visaTransformableService, PasseportService passeportService) {
        this.visaTransformableService = visaTransformableService;
        this.passeportService = passeportService;
    }

    @GetMapping("/new")
    public String createForm(@RequestParam Integer passeportId, Model model) {
        Passeport passeport = passeportService.findById(passeportId);
        model.addAttribute("passeport", passeport);
        model.addAttribute("demandeur", passeport.getDemandeur());
        return "visa_transformable/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        com.projet.visa.model.VisaTransformable visaTransformable = visaTransformableService.findById(id);
        Passeport passeport = visaTransformable.getPasseport();
        model.addAttribute("visaTransformable", visaTransformable);
        model.addAttribute("passeport", passeport);
        model.addAttribute("demandeur", passeport.getDemandeur());
        return "visa_transformable/form";
    }

    @PostMapping("")
    public String create(
            @RequestParam Integer passeportId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEntree,
            @RequestParam String reference,
            @RequestParam String lieu,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiration,
            RedirectAttributes redirectAttributes) {

        Passeport passeport = passeportService.findById(passeportId);
        Integer demandeurId = passeport.getDemandeur().getId();

        try {
            visaTransformableService.create(dateEntree, reference, lieu, expiration, passeportId);
            redirectAttributes.addFlashAttribute("success", "Visa transformable enregistre avec succes");
            return "redirect:/demandeurs/" + demandeurId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/visa_transformables/new?passeportId=" + passeportId;
        }
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Integer id,
            @RequestParam Integer passeportId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEntree,
            @RequestParam String reference,
            @RequestParam String lieu,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiration,
            RedirectAttributes redirectAttributes) {

        Passeport passeport = passeportService.findById(passeportId);
        Integer demandeurId = passeport.getDemandeur().getId();

        try {
            visaTransformableService.update(id, dateEntree, reference, lieu, expiration);
            redirectAttributes.addFlashAttribute("success", "Visa transformable modifie avec succes");
            return "redirect:/demandeurs/" + demandeurId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/visa_transformables/" + id + "/edit";
        }
    }
}
