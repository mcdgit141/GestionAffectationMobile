package com.epita.filrouge.expositions.collaborateur;

import com.epita.filrouge.domain.affectation.Affectation;

import java.util.List;

public class CollaborateurFullDTO {
    private String uid;
    private String nom;
    private String prenom;
    private String numeroLigne;

//    private List<Affectation> affectationCollaborateur;

    public CollaborateurFullDTO(String uid, String nom, String prenom, String numeroLigne) {
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroLigne = numeroLigne;
//        this.affectationCollaborateur = affectationCollaborateur;
    }


}
