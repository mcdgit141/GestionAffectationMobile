package com.epita.filrouge.domain.collaborateur;

import com.epita.filrouge.domain.affectation.Affectation;

import com.epita.filrouge.domain.affectation.AffectationNumeroGenerateur;
import com.epita.filrouge.domain.exception.AllReadyExistException;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.uo.Uo;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Collaborateur {

    private String uid;
    private String nom;
    private String prenom;
    private String numeroLigne;
    private Uo uo;

    private List<Affectation> affectationCollaborateur;

    public Collaborateur(String uid, String nom, String prenom, String numeroLigne, Uo uo) {
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroLigne = numeroLigne;
        this.uo = uo;

        this.affectationCollaborateur = new ArrayList<Affectation>();
    }

    public void setNumeroLigne(String numeroLigne) {
        this.numeroLigne = numeroLigne;
    }

    public String getUid() {
        return uid;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumeroLigne() {
        return numeroLigne;
    }

    public Uo getUo() {
        return uo;
    }

    public List<Affectation> getAffectationCollaborateur() {
        return affectationCollaborateur;
    }

    public void addAffectationCollaborateur(final Affectation affectation){
        affectationCollaborateur.add(affectation);
    }

    public void affecterNumeroLigne (String numeroLigne){

    }

    public Collaborateur miseAJourCollaborateurSuiteClotureAffectation() {
        this.numeroLigne = null;
        return this;
    }

//    public boolean controlCollaborateurEstSansAffectationEnCours() {
//        if (affectationCollaborateur != null) {
//            //faire la boucle for pour test de la date de fin. Si la date de fin est à NULL, l'affectation existe déjà donc refuser la création
//
//            for (final Affectation affectations : affectationCollaborateur) {
//                if (affectations.getDateFin() == null) {
//                    throw new AllReadyExistException("L'affectation pour ce collaborateur existe déjà, merci de la clôturer au préalable : " + affectations.getCollaborateur().getUid());
//                }
//            }
//        }
//        return true;
//    }
//
//    public Affectation nouvelleAffectation(Iphone iPhone, LocalDate dateAffectation, String commentaire) {
//        if (!controlCollaborateurEstSansAffectationEnCours() {
//            throws Pas possible
//        }
//        if (!iPhone.controlDisponibiliteIphone()) {
//            throws Iphone pas dispo
//        }
//
//        Affectation affectation = new Affectation(AffectationNumeroGenerateur.genererNumeroAffectation(), dateAffectation, commentaire, this, iPhone);
//
//        affectationCollaborateur.add(affectation);
//
//        iPhone.addAffectation(affectation);
//
//        return affectation;
//    }
}
