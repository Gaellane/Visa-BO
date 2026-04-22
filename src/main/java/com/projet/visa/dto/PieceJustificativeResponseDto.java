package com.projet.visa.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PieceJustificativeResponseDto {

    private List<PieceJustificativeDto> piecesCommunes = new ArrayList<>();
    private List<PieceJustificativeDto> piecesPropres = new ArrayList<>();
}
