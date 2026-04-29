package com.projet.visa.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.projet.visa.model.DemandeType;
import com.projet.visa.model.DemandeStatus;
import com.projet.visa.model.Demandeur;
import com.projet.visa.model.VisaType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DemandeListDto {
    private Integer id;

    private LocalDate dateDemande;

    private String numero;

    private Demandeur demandeur;

    private DemandeType type;

    private VisaType typeVisa;

    private DemandeStatus status;


}
