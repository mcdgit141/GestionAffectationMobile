package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.iphone.IphoneEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AffectationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numeroAffectation;
    private Date dateAttribution;
    private Date dateRenouvellementPrevue;
    private Date dateFin;
    private String commentaire;
    private String motifFin;

    @ManyToOne
    @JoinColumn(name = "collaborateurId")
    private CollaborateurEntity collaborateur;

    @ManyToOne
    @JoinColumn(name = "iphoneId")
    private IphoneEntity iphone;

    public AffectationEntity() {

    }
}
