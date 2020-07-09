package com.epita.filrouge.infrastructure.collaborateur;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.infrastructure.affectation.AffectationEntity;
import com.sun.tracing.dtrace.ModuleName;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class CollaborateurEntity {

    @Id
    private String uid;

    private String nom;
    private String prenom;

    @OneToMany(mappedBy = "collaborateur")
    private List<AffectationEntity> affectationCollaborateur;

    public String getUid() {
        return uid;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public List<AffectationEntity> getAffectationCollaborateur() {
        return affectationCollaborateur;
    }
}
