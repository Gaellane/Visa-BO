package com.projet.visa.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet.visa.dto.DemandeListDto;
import com.projet.visa.dto.DemandeWithStatusDto;
import com.projet.visa.service.DemandeService;

@RestController
@RequestMapping("/api/demandes")
public class RestDemandeController {
    private final DemandeService demandeService;

    public RestDemandeController(DemandeService demandeService) {
        this.demandeService = demandeService;
    }

    @GetMapping("/{numero}")
    public ResponseEntity<List<DemandeListDto>> getByNumeroPath(@PathVariable String numero) {
        try {
            List<DemandeListDto> demandes = demandeService.getByPasseportOrDemandeNumero(numero);
            return ResponseEntity.ok(demandes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<DemandeWithStatusDto> getDtoWithHistoryById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(demandeService.getDtoWithHistoryById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
