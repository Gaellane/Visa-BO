package com.projet.visa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projet.visa.dto.PieceJustificativeDto;
import com.projet.visa.dto.PieceJustificativeResponseDto;
import com.projet.visa.model.PieceJustificative;
import com.projet.visa.repository.PieceJustificativeRepository;

@Service
public class PieceJustificativeService {
    private static final Integer TYPE_DEMANDE_CREATION_ID = 1;

    private final PieceJustificativeRepository pieceJustificativeRepository;

    public PieceJustificativeService(PieceJustificativeRepository pieceJustificativeRepository) {
        this.pieceJustificativeRepository = pieceJustificativeRepository;
    }

    public PieceJustificativeResponseDto loadByVisaType(Integer typeVisaId, Integer typeDemandeId) {
        PieceJustificativeResponseDto response = new PieceJustificativeResponseDto();

        if (typeDemandeId == null || typeVisaId == null || !TYPE_DEMANDE_CREATION_ID.equals(typeDemandeId)) {
            return response;
        }

        List<PieceJustificative> pieces = pieceJustificativeRepository.findByTypeVisa_IdIsNullOrTypeVisa_Id(typeVisaId);
        for (PieceJustificative piece : pieces) {
            PieceJustificativeDto pieceDto = new PieceJustificativeDto(
                    piece.getId(),
                    piece.getCode(),
                    piece.getNomPiece(),
                    piece.getObligatoire(),
                    piece.getTypeVisa() == null ? null : piece.getTypeVisa().getValeur());

            if (piece.getTypeVisa() == null) {
                response.getPiecesCommunes().add(pieceDto);
            } else {
                response.getPiecesPropres().add(pieceDto);
            }
        }
        return response;
    }

    
}
