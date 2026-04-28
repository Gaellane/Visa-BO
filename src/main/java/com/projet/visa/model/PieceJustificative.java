package com.projet.visa.model;

import jakarta.persistence.Column;
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
@Table(name = "piece_justificative")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PieceJustificative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "nom_piece")
    private String nomPiece;

    @ManyToOne
    @JoinColumn(name = "id_type_visa")
    private VisaType typeVisa;

    @Column(name = "obligatoire", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean obligatoire= true;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
