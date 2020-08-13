package com.epita.filrouge.domain.affectation;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.exception.AllReadyClotureeException;
import com.epita.filrouge.domain.exception.NotFoundException;
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

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Affectation reglesAppliqueesPourCloturerAffectation(Collaborateur collaborateur, Iphone iphone, String affectationCommentaire,String motifFin,LocalDate dateFin) throws AllReadyClotureeException{

        if (this.getDateFin() != null){
            throw new AllReadyClotureeException("L'affectation avec le numéro suivant est déjà clôturée " + this.getNumeroAffectation());
        }

        if (dateFin != null)
            {this.setDateFin(dateFin);}
        else
            {this.setDateFin(LocalDate.now());}
       this.setMotifFin(motifFin);
       this.setCommentaire(affectationCommentaire);
       this.collaborateur.miseAJourCollaborateurSuiteClotureAffectation();
       this.iphone.miseAJourIphoneSuiteClotureAffectation();
//       this.collaborateur = collaborateur;
//       this.iphone = iphone;

        return this;
    }

    public void reglesAppliqueesPourSuppressionAffectation(){

        this.collaborateur.miseAJourCollaborateurSuiteClotureAffectation();

        this.iphone.miseAJourIphoneSuiteClotureAffectation();

    }
}
