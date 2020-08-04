package com.epita.filrouge.infrastructure.collaborateur;

import com.epita.filrouge.infrastructure.affectation.AffectationEntity;
import com.epita.filrouge.infrastructure.site.SiteExerciceEntity;
import com.epita.filrouge.infrastructure.uo.UoEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name ="Collaborateur")
public class CollaborateurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collaborateurId;

    private String uid;

    private String nom;
    private String prenom;

    private String numeroLigne;

    @ManyToOne(fetch = FetchType.LAZY  )
    @JoinColumn(name = "uoId", nullable = false)
    private UoEntity uo;

    /*@ManyToOne(fetch = FetchType.LAZY  )
    @JoinColumn(name = "siteId", nullable = false)
    private SiteExerciceEntity siteExercice;*/  //le site a été mis au niveau de l' UO

//    @OneToMany(mappedBy = "collaborateur") //permet à partir de collaborateur de consulter les affectations
//    private List<AffectationEntity> affectationCollaborateur = new ArrayList<>();

    public String getNumeroLigne() {
        return numeroLigne;
    }

    public void setNumeroLigne(String numeroLigne) {
        this.numeroLigne = numeroLigne;
    }

    public CollaborateurEntity() {

    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

    public Long getCollaborateurId() {
        return collaborateurId;
    }

    public void setCollaborateurId(Long collaborateurId) {
        this.collaborateurId = collaborateurId;
    }

    public UoEntity getUo() {
        return uo;
    }

    public void setUo(UoEntity uo) {
        this.uo = uo;
    }

   /* public SiteExerciceEntity getSiteExercice() {
        return siteExercice;
    }
    public void setSiteExercice(SiteExerciceEntity siteExercice) {
        this.siteExercice = siteExercice;
    }*/
}
