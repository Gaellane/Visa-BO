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
@Table(name = "passeport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passeport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "delivrance")
    private LocalDate delivrance;

    @Column(name = "expiration")
    private LocalDate expiration;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_demandeur", nullable = false)
    private Demandeur demandeur;
}
