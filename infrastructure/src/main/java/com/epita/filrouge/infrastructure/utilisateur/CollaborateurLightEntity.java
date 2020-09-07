package com.epita.filrouge.infrastructure.utilisateur;

import javax.persistence.Embeddable;

@Embeddable
public class CollaborateurLightEntity {

    private String uid;
    private String nom;
    private String prenom;

    public CollaborateurLightEntity() {
    }

    public CollaborateurLightEntity(String uid, String nom, String prenom) {
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
