package com.projet.visa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "visa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "date_entree", nullable = false)
    private LocalDate dateEntree;

    @Column(name = "date_expiration", nullable = false)
    private LocalDate dateExpiration;

    @Column(name = "lieu_entree")
    private String lieuEntree;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_demande", nullable = false)
    private Demande demande;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_passport", nullable = false)
    private Passeport passeport;
}
