package com.epita.filrouge.expositions.affectation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuppressionDTO_PourPoubelle {
    @JsonProperty
    Long numeroAffectation;

    @JsonProperty
    String commentaire;

    public SuppressionDTO_PourPoubelle(Long numeroAffectation, String commentaire) {
        this.numeroAffectation = numeroAffectation;
        this.commentaire = commentaire;
    }

    public SuppressionDTO_PourPoubelle() {
    }

    @Override
    public String toString() {
        return "SuppressionDTO{" +
                "numeroAffectation=" + numeroAffectation +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }


    public void setNumeroAffectation(Long numeroAffectation) {
        this.numeroAffectation = numeroAffectation;
    }

}
