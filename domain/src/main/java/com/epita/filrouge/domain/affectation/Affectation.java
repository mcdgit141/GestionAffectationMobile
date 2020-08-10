package com.epita.filrouge.domain.affectation;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.iphone.Iphone;

import java.time.LocalDate;

public class Affectation {

    private Long numeroAffectation;
    private LocalDate dateAffectation;
    private LocalDate dateRenouvellementPrevue;
    private LocalDate dateFin;
    private String commentaire;
    private String motifFin;

    private Collaborateur collaborateur;
    private Iphone iphone;

    public Affectation(Long numeroAffectation, LocalDate dateAffectation, String commentaire, Collaborateur collaborateur, Iphone iphone) {
        this.numeroAffectation = numeroAffectation;
        this.dateAffectation = dateAffectation;
        this.commentaire = commentaire;
        this.collaborateur = collaborateur;
        this.iphone = iphone;
        calculDateRenouvellement();

    }

    public Long getNumeroAffectation() {
        return numeroAffectation;
    }

    public LocalDate getDateAffectation() {
        return dateAffectation;
    }

    public LocalDate getDateRenouvellementPrevue() {
        return dateRenouvellementPrevue;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getMotifFin() {
        return motifFin;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public Iphone getIphone() {
        return iphone;
    }

    private void calculDateRenouvellement() {
        dateRenouvellementPrevue = dateAffectation.plusYears(2);
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setMotifFin(String motifFin) {
        this.motifFin = motifFin;
    }

    public void setDateRenouvellementPrevue(LocalDate dateRenouvellementPrevue) {
        this.dateRenouvellementPrevue = dateRenouvellementPrevue;
    }

}
