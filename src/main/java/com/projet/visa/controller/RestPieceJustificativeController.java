package com.projet.visa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet.visa.dto.PieceJustificativeResponseDto;
import com.projet.visa.service.PieceJustificativeService;

@RestController
@RequestMapping("/api/pieces-justificatives")
public class RestPieceJustificativeController {

	private final PieceJustificativeService pieceJustificativeService;

	public RestPieceJustificativeController(PieceJustificativeService pieceJustificativeService) {
		this.pieceJustificativeService = pieceJustificativeService;
	}

	@GetMapping("/{typeVisaId}")
	public PieceJustificativeResponseDto loadByVisaType(@PathVariable Integer typeVisaId,
			@RequestParam Integer typeDemandeId) {
		return pieceJustificativeService.loadByVisaType(typeVisaId, typeDemandeId);
	}
}
