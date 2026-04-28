package com.projet.visa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PieceJustificativeDto {

    private Integer id;
    private String code;
    private String nomPiece;
    private Boolean obligatoire;
    private String typeVisa;
}
