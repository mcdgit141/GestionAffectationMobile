package com.epita.filrouge.domain.collaborateur;

import com.epita.filrouge.domain.uo.Uo;

public class Collaborateur {

    private Long id;

    private String uid;
    private String nom;
    private String prenom;
    private String numeroLigne;
    private Uo uo;

//    private List<Affectation> affectationCollaborateur;

    public Collaborateur(String uid, String nom, String prenom, String numeroLigne, Uo uo) {
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroLigne = numeroLigne;
        this.uo = uo;

//        this.affectationCollaborateur = new ArrayList<Affectation>();
    }

    public Collaborateur(Long id, String uid, String nom, String prenom, String numeroLigne, Uo uo) {
        this.id = id;
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroLigne = numeroLigne;
        this.uo = uo;
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



    public Collaborateur miseAJourCollaborateurSuiteClotureAffectation() {
        this.numeroLigne = null;
        return this;
    }

    public Collaborateur miseAJourCollaborateurSuiteCreationAffectation(String numeroLigne) {
        this.numeroLigne = numeroLigne;
        return this;
    }

}
