package com.epita.filrouge.domain.affectation;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.iphone.Iphone;

import java.util.Date;

public class Affectation {

    private Long idAffectation;
    private Date dateAttribution;
    private Date dateRenouvellementPrevue;
    private Date getDateRenouvellementEffective;
    private String commentaire;
    private String motifFin;

    private Collaborateur collaborateur;
    private Iphone iphone;

    public Affectation(Long idAffectation, Date dateAttribution, Date dateRenouvellementPrevue, Date getDateRenouvellementEffective, String commentaire, String motifFin, Collaborateur collaborateur, Iphone iphone) {
        this.idAffectation = idAffectation;
        this.dateAttribution = dateAttribution;
        this.dateRenouvellementPrevue = dateRenouvellementPrevue;
        this.getDateRenouvellementEffective = getDateRenouvellementEffective;
        this.commentaire = commentaire;
        this.motifFin = motifFin;
        this.collaborateur = collaborateur;
        this.iphone = iphone;
    }

    public Long getIdAffectation() {
        return idAffectation;
    }

    public Date getDateAttribution() {
        return dateAttribution;
    }

    public Date getDateRenouvellementPrevue() {
        return dateRenouvellementPrevue;
    }

    public Date getGetDateRenouvellementEffective() {
        return getDateRenouvellementEffective;
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
