package com.epita.filrouge.infrastructure.iphone;

import javax.persistence.*;

@Entity
@Table(name = "ModeleIphone")
public class ModeleIphoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modeleId;
    private String nomModele;

    public ModeleIphoneEntity() {
          }

    public Long getModeleId() {
        return modeleId;
    }

    public String getNomModele() {
        return nomModele;
    }

    public void setModeleId(Long modeleId) {
        this.modeleId = modeleId;
    }
    public void setNomModele(String nomModele) {
        this.nomModele = nomModele;
    }
}
