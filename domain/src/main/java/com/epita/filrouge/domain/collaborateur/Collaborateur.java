package com.epita.filrouge.domain.collaborateur;

import com.epita.filrouge.domain.affectation.Affectation;

import java.util.ArrayList;
import java.util.List;

public class Collaborateur {

    private String uid;
    private String nom;
    private String prenom;
    private String numeroLigne;

    private List<Affectation> affectationCollaborateur;

    public Collaborateur(String uid, String nom, String prenom) {
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
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

    public List<Affectation> getAffectation() {
        return affectationCollaborateur;
    }
}
