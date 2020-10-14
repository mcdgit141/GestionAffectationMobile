package com.epita.filrouge.domain.iphone;

import com.epita.filrouge.domain.exception.AllReadyExistException;

public class Iphone {

    private Long iphoneId;

    private String numeroSerie;
    private double prixIphone;

    private ModeleIphone modeleIphone;

    private EtatIphoneEnum etatIphone;


    public Iphone(Long iphoneId, String numeroSerie, double prixIphone, ModeleIphone modeleIphone, EtatIphoneEnum etatIphone) {
        this.iphoneId = iphoneId;
        this.numeroSerie = numeroSerie;
        this.prixIphone = prixIphone;
        this.modeleIphone = modeleIphone;
        this.etatIphone = etatIphone;
    }

    public Iphone() {
    }

    public Long getIphoneId() {
        return iphoneId;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public EtatIphoneEnum getEtatIphone() {
        return etatIphone;
    }


    public double getPrixIphone() {
        return prixIphone;
    }

    public ModeleIphone getModeleIphone() {
        return modeleIphone;
    }



    public void setEtatIphone(EtatIphoneEnum etatIphone) {
        this.etatIphone = etatIphone;
    }

    public Iphone miseAJourIphoneSuiteClotureAffectation(String motifFin) {

        if (this.etatIphone == EtatIphoneEnum.AFFECTE) {
            if (motifFin.equals("RESTITUE") || motifFin.equals("SUPPRIME")) {
                this.etatIphone = EtatIphoneEnum.DISPONIBLE;
            }

            if (motifFin.equals("VOLE")) {
                this.etatIphone = EtatIphoneEnum.VOLE;
            }
            if (motifFin.equals("CASSE")) {
                this.etatIphone = EtatIphoneEnum.CASSE;
            }
            if (motifFin.equals("PERDU")) {
                this.etatIphone = EtatIphoneEnum.PERDU;
            }
        }
        return  this;
    }

    public boolean controlDisponibiliteIphone() {
        if (etatIphone != EtatIphoneEnum.DISPONIBLE) {
            throw new AllReadyExistException("Cet iPhone n'est pas disponible, merci de recommencer : " + getNumeroSerie());
        }
        return true;
    }

    public Iphone miseAJourIphoneSuiteCreationAffectation() {

        if (this.etatIphone == EtatIphoneEnum.DISPONIBLE){
            this.etatIphone = EtatIphoneEnum.AFFECTE;
        }
        return this;
    }
}
