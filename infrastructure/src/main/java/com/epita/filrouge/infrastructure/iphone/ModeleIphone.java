package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.infrastructure.affectation.AffectationEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class ModeleIphone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modeleId;

    private String nomModele;

    public ModeleIphone() {
          }

    public Long getModeleId() {
        return modeleId;
    }

    public String getNomModele() {
        return nomModele;
    }

}
