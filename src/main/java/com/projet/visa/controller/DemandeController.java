package com.projet.visa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projet.visa.model.Demande;
import com.projet.visa.repository.DemandeRepository;


@Controller
@RequestMapping("/demande")
public class DemandeController {

    private final DemandeRepository demandeRepository;

    public DemandeController(DemandeRepository demandeRepository) {
        this.demandeRepository = demandeRepository;
    }

    @GetMapping("/liste")
    public String getListeDemande(Model model) {
        List<Demande> demandes = demandeRepository.findAll();
        model.addAttribute("demandes", demandes);

        return "demande/liste";
    }

}
