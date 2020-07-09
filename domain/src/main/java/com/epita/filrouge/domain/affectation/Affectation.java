package com.epita.filrouge.domain.affectation;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.iphone.Iphone;

import java.util.Date;

public class Affectation {

    private Long numeroAffectation;
    private Date dateAttribution;
    private Date dateRenouvellementPrevue;
    private Date dateRenouvellementEffective;
    private String commentaire;
    private String motifFin;

    private Collaborateur collaborateur;
    private Iphone iphone;

    public Affectation(Long numeroAffectation, Date dateAttribution, Date dateRenouvellementPrevue, Date dateRenouvellementEffective, String commentaire, String motifFin, Collaborateur collaborateur, Iphone iphone) {
        this.numeroAffectation = numeroAffectation;
        this.dateAttribution = dateAttribution;
        this.dateRenouvellementPrevue = dateRenouvellementPrevue;
        this.dateRenouvellementEffective = dateRenouvellementEffective;
        this.commentaire = commentaire;
        this.motifFin = motifFin;
        this.collaborateur = collaborateur;
        this.iphone = iphone;
    }

    public Long getNumeroAffectation() {
        return numeroAffectation;
    }

    public Date getDateAttribution() {
        return dateAttribution;
    }

    public Date getDateRenouvellementPrevue() {
        return dateRenouvellementPrevue;
    }

    public Date getDateRenouvellementEffective() {
        return dateRenouvellementEffective;
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
}
