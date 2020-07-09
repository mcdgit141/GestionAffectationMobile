package com.epita.filrouge.infrastructure.collaborateur;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.infrastructure.affectation.AffectationEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CollaborateurEntity {

    @Id
    private String uid;

    private String nom;
    private String prenom;

//    @OneToMany(mappedBy = "collaborateur")
//    private List<AffectationEntity> affectationCollaborateur = new ArrayList<>();



    public String getUid() {
        return uid;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

//    public List<AffectationEntity> getAffectationCollaborateur() {
//        return affectationCollaborateur;
//    }
}
