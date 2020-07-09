package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.iphone.Iphone;

import javax.persistence.Id;
import java.io.Serializable;

public class AffectationId implements Serializable {
    private Collaborateur collaborateur;

    private Iphone iphone;

    public AffectationId(Collaborateur collaborateur, Iphone iphone) {
        this.collaborateur = collaborateur;
        this.iphone = iphone;
    }
}
