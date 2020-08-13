package com.epita.filrouge.expositions.affectation;

import java.time.LocalDate;

public class AffectationDTO {

    private Long numeroAffectation;
    private String collaborateurUid;
    private String iphoneNumeroSerie;
    private LocalDate affectationDate;
    private String collaborateurNumeroLigne;
    private String affectationCommentaire;
    private String motifFin;
    private LocalDate dateFin;

    public AffectationDTO() {
    }

    public String getCollaborateurUid() {
        return collaborateurUid;
    }

    public void setCollaborateurUid(String collaborateurUid) {
        this.collaborateurUid = collaborateurUid;
    }

    public String getIphoneNumeroSerie() {
        return iphoneNumeroSerie;
    }

    public void setIphoneNumeroSerie(String iphoneNumeroSerie) {
        this.iphoneNumeroSerie = iphoneNumeroSerie;
    }

    public LocalDate getAffectationDate() {
        return affectationDate;
    }

    public void setAffectationDate(LocalDate affectationDate) {
        this.affectationDate = affectationDate;
    }

    public String getCollaborateurNumeroLigne() {
        return collaborateurNumeroLigne;
    }

    public void setCollaborateurNumeroLigne(String collaborateurNumeroLigne) {
        this.collaborateurNumeroLigne = collaborateurNumeroLigne;
    }

    public String getAffectationCommentaire() {
        return affectationCommentaire;
    }

    public void setAffectationCommentaire(String affectationCommentaire) {
        this.affectationCommentaire = affectationCommentaire;
    }

    public Long getNumeroAffectation() {
        return numeroAffectation;
    }

    public void setNumeroAffectation(Long numeroAffectation) {
        this.numeroAffectation = numeroAffectation;
    }

    public String getMotifFin() {
        return motifFin;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setMotifFin(String motifFin) {
        this.motifFin = motifFin;
    }
}
