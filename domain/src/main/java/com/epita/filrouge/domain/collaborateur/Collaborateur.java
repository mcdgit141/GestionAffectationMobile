package com.epita.filrouge.domain.collaborateur;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;

import java.util.ArrayList;
import java.util.List;

public class Collaborateur {

    private Long id;

    private String uid;
    private String nom;
    private String prenom;
    private String numeroLigne;
    private Uo uo;
    /*private SiteExercice siteExercice;*/

    private List<Affectation> affectationCollaborateur;

    public Collaborateur(String uid, String nom, String prenom, String numeroLigne) {
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroLigne = numeroLigne;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Uo getUo() {
        return uo;
    }

    /*public SiteExercice getSiteExercice() {
        return siteExercice;
    }*/

    public List<Affectation> getAffectationCollaborateur() {
        return affectationCollaborateur;
    }
}
