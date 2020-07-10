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
}
