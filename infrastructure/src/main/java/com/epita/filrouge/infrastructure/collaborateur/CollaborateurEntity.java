package com.epita.filrouge.infrastructure.collaborateur;


import com.epita.filrouge.infrastructure.uo.UoEntity;

import javax.persistence.*;


@Entity
public class CollaborateurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collaborateurId;

    @Column(unique = true)
    private String uid;

    private String nom;
    private String prenom;

    private String numeroLigne;

    @ManyToOne(fetch = FetchType.LAZY  )
    @JoinColumn(name = "uoId", nullable = false)
    private UoEntity uo;



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


}
