package com.projet.visa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "demande_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DemandeStatus {

    @Id
    private Integer id;

    @Column(name = "valeur")
    private String valeur;
}
