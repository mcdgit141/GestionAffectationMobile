package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.ModeleEnum;
import com.epita.filrouge.infrastructure.affectation.AffectationEntity;

import javax.persistence.*;
import java.util.List;
@Entity
public class IphoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iphoneId;


    private String numeroSerie;
    private double prixIphone;

//    @ElementCollection
    @Enumerated(EnumType.STRING)
    private ModeleEnum modele;

//    @ElementCollection
    @Enumerated(EnumType.STRING)
    private EtatIphoneEnum etatIphone;

    @OneToMany(mappedBy = "iphone" )
    private List<AffectationEntity> affectationIphone;

    public IphoneEntity () {

    }

    public void setIphoneId(Long iphoneId) {
        this.iphoneId = iphoneId;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public void setPrixIphone(double prixIphone) {
        this.prixIphone = prixIphone;
    }

    public void setModele(ModeleEnum modele) {
        this.modele = modele;
    }

    public void setEtatIphone(EtatIphoneEnum etatIphone) {
        this.etatIphone = etatIphone;
    }

    public void setAffectationIphone(List<AffectationEntity> affectationIphone) {
        this.affectationIphone = affectationIphone;
    }
}
