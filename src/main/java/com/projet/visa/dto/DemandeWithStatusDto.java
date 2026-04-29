package com.projet.visa.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import com.projet.visa.model.DemandeHistory;
import com.projet.visa.model.DemandeStatus;
import com.projet.visa.model.DemandeType;
import com.projet.visa.model.Demandeur;
import com.projet.visa.model.VisaType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DemandeWithStatusDto {
    private DemandeListDto demande;

    private List <DemandeHistory> histories = new ArrayList<>();
}
