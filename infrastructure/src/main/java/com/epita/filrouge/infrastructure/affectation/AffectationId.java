package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.iphone.Iphone;

import javax.persistence.Id;
import java.io.Serializable;

public class AffectationId implements Serializable {
    private String uid;

    private String numeroSerie;

    public AffectationId(String uid, String numeroSerie) {
        this.uid = uid;
        this.numeroSerie = numeroSerie;
    }
}
