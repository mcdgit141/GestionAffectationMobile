package com.epita.filrouge.expositions.affectation;

import com.epita.filrouge.expositions.collaborateur.CollaborateurFullDTO;
import com.epita.filrouge.expositions.iphone.IphoneFullDTO;

import java.time.LocalDate;

public class AffectationFullDTO {
    private Long numeroAffectation;
    private LocalDate dateAffectation;
    private LocalDate dateRenouvellementPrevue;
    private LocalDate dateFin;
    private String commentaire;
    private String motifFin;

    private CollaborateurFullDTO collaborateur;
    private IphoneFullDTO iphone;

    public AffectationFullDTO() {
    }

//    public AffectationFullDTO(Long numeroAffectation, LocalDate dateAffectation, LocalDate dateRenouvellementPrevue, LocalDate dateFin, String commentaire, String motifFin, CollaborateurFullDTO collaborateurFullDTO, IphoneFullDTO iphoneFullDTO) {
//        this.numeroAffectation = numeroAffectation;
//        this.dateAffectation = dateAffectation;
//        this.dateRenouvellementPrevue = dateRenouvellementPrevue;
//        this.dateFin = dateFin;
//        this.commentaire = commentaire;
//        this.motifFin = motifFin;
//        this.collaborateurFullDTO = collaborateurFullDTO;
//        this.iphoneFullDTO = iphoneFullDTO;
//    }

    public Long getNumeroAffectation() {
        return numeroAffectation;
    }

    public void setNumeroAffectation(Long numeroAffectation) {
        this.numeroAffectation = numeroAffectation;
    }

    public LocalDate getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(LocalDate dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public LocalDate getDateRenouvellementPrevue() {
        return dateRenouvellementPrevue;
    }

    public void setDateRenouvellementPrevue(LocalDate dateRenouvellementPrevue) {
        this.dateRenouvellementPrevue = dateRenouvellementPrevue;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getMotifFin() {
        return motifFin;
    }

    public void setMotifFin(String motifFin) {
        this.motifFin = motifFin;
    }

    public CollaborateurFullDTO getCollaborateur() {
        return collaborateur;
    }

    public void setCollaborateur(CollaborateurFullDTO collaborateur) {
        this.collaborateur = collaborateur;
    }

    public IphoneFullDTO getIphone() {
        return iphone;
    }

    public void setIphone(IphoneFullDTO iphoneFullDTO) {
        this.iphone = iphoneFullDTO;
    }
}
