package com.projet.visa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "demande_piece")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DemandePiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_demande", nullable = false)
    private Demande demande;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_piece", nullable = false)
    private PieceJustificative piece;
}
