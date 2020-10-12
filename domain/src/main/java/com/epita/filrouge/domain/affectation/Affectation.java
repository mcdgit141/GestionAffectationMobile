package com.epita.filrouge.domain.affectation;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.exception.AllReadyClotureeException;
import com.epita.filrouge.domain.exception.AllReadyExistException;

import com.epita.filrouge.domain.iphone.Iphone;

import java.time.LocalDate;
import java.util.List;

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

        if (this.dateFin != null){
            throw new AllReadyClotureeException("L'affectation avec le numéro suivant est déjà clôturée " + this.numeroAffectation);
        }

        if (dateFin != null)
            {this.dateFin=dateFin;}
        else
            {this.dateFin=LocalDate.now();}

       this.motifFin=motifFin;
       this.commentaire= affectationCommentaire;
       this.collaborateur.miseAJourCollaborateurSuiteClotureAffectation();
       this.iphone.miseAJourIphoneSuiteClotureAffectation(motifFin);

        return this;
    }

    public void reglesAppliqueesPourSuppressionAffectation(){

        if (this.getDateFin() != null) {
            throw new AllReadyClotureeException("Cette affectation a une date de fin renseignée. Elle ne peut donc être supprimée.");
        }

        if (this.getDateAffectation().compareTo(LocalDate.now()) < 0)  {
            throw new AllReadyClotureeException("Cette affectation a une date d'affectation anterieure à aujourd'hui. Elle ne peut donc être supprimée.");
        }

        this.collaborateur.miseAJourCollaborateurSuiteClotureAffectation();

        this.iphone.miseAJourIphoneSuiteClotureAffectation("SUPPRIME");

    }

    public Affectation reglesAppliqueesPourLaCreation(Collaborateur collaborateur, Iphone iphone, String numeroLigne, List<Affectation> affectationDejaCree ) throws AllReadyClotureeException{

        if (this.getDateAffectation().compareTo(LocalDate.now()) < 0)  {
            throw new AllReadyClotureeException("La date d'affectation a une date antérieure à celle de ce jour. L'affectation ne peut pas être créée.");
        }

        if (affectationDejaCree != null) {
           //faire la boucle for pour test de la date de fin. Si la date de fin est à NULL, l'affectation existe déjà donc refuser la création

            for (final Affectation affectations : affectationDejaCree) {
                if (affectations.getDateFin() == null) {
                   throw new AllReadyExistException("L'affectation pour ce collaborateur existe déjà, merci de la clôturer au préalable : " + affectations.getCollaborateur().getUid());
               }
           }
       }

        if (!iphone.controlDisponibiliteIphone()) {
            throw new AllReadyExistException("Cet iPhone n'est pas disponible, merci de recommencer : " + iphone.getNumeroSerie());
        }

        this.collaborateur.miseAJourCollaborateurSuiteCreationAffectation(numeroLigne);
        this.iphone.miseAJourIphoneSuiteCreationAffectation();

        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "numeroAffectation=" + numeroAffectation +
                ", dateAffectation=" + dateAffectation +
                ", dateRenouvellementPrevue=" + dateRenouvellementPrevue +
                ", dateFin=" + dateFin +
                ", commentaire='" + commentaire + '\'' +
                ", motifFin='" + motifFin + '\'' +
                ", collaborateur=" + collaborateur +
                ", iphone=" + iphone +
                '}';
    }
}
