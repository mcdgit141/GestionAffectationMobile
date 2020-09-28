package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.domain.iphone.EtatIphoneEnum;

import javax.persistence.*;

@Entity
public class IphoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iphoneId;

    @Column(unique = true)
    private String numeroSerie;
    private double prixIphone;

    @ManyToOne(fetch = FetchType.LAZY  )
    @JoinColumn(name = "modeleId", nullable = false)
    private ModeleIphoneEntity modeleIphoneEntity;

    @Enumerated(EnumType.STRING)
    private EtatIphoneEnum etatIphone;


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


    public void setEtatIphone(EtatIphoneEnum etatIphone) {
        this.etatIphone = etatIphone;
    }

    public ModeleIphoneEntity getModeleIphoneEntity() {
        return modeleIphoneEntity;
    }

    public void setModeleIphoneEntity(ModeleIphoneEntity modeleIphoneEntity) {
        this.modeleIphoneEntity = modeleIphoneEntity;
    }

    public Long getIphoneId() {
        return iphoneId;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public double getPrixIphone() {
        return prixIphone;
    }

    public EtatIphoneEnum getEtatIphone() {
        return etatIphone;
    }



    @Override
    public String toString() {
        return "\n---------------\nHashcode: "+ this.hashCode() +"\n"
                + "Etat: "+this.getEtatIphone().toString() +"\n"
                + "Id: " + this.getIphoneId()  +"\n"
                + "NumeroSerie: " + this.getNumeroSerie()  +"\n"
                + "ModeleEntityIphone: " + this.getModeleIphoneEntity()  +"\n"
                + "PrixIphone: " + this.getPrixIphone()
                + "\n---------------\n"
                ;
    }
}
