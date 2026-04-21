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
@Table(name = "demandeur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Demandeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom_jeune_fille")
    private String nomJeuneFille;

    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Column(name = "mail")
    private String mail;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "tel", nullable = false)
    private String tel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_status_marital", nullable = false)
    private StatusMarital statusMarital;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nationalite", nullable = false)
    private Nationalite nationalite;
}
