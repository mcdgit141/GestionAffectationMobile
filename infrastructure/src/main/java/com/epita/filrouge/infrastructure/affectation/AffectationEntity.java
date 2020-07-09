package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.iphone.Iphone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;

@Entity
@IdClass(AffectationId.class)
public class AffectationEntity {

    private Long numeroAffectation;
    private Date dateAttribution;
    private Date dateRenouvellementPrevue;
    private Date dateRenouvellementEffective;
    private String commentaire;
    private String motifFin;

    @Id
    private Collaborateur collaborateur;

    @Id
    private Iphone iphone;
}
