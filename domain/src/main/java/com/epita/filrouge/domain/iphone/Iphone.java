package com.epita.filrouge.domain.iphone;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.exception.AllReadyExistException;


import java.util.ArrayList;
import java.util.List;

public class Iphone {

    private Long iphoneId;

    private String numeroSerie;
    private double prixIphone;

    private ModeleIphone modeleIphone;

    private EtatIphoneEnum etatIphone;

//    private List<Affectation> affectationIphone;

    public Iphone(Long iphoneId, String numeroSerie, double prixIphone, ModeleIphone modeleIphone, EtatIphoneEnum etatIphone) {
        this.iphoneId = iphoneId;
        this.numeroSerie = numeroSerie;
        this.prixIphone = prixIphone;
        this.modeleIphone = modeleIphone;
        this.etatIphone = etatIphone;
//        this.affectationIphone = new ArrayList<Affectation>();
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

//    public List<Affectation> getAffectationIphone() {
//        return affectationIphone;
//    }

    public double getPrixIphone() {
        return prixIphone;
    }

    public ModeleIphone getModeleIphone() {
        return modeleIphone;
    }

//    public void addAffectation(final Affectation affectation){
//        affectationIphone.add(affectation);
//            }

    public void setEtatIphone(EtatIphoneEnum etatIphone) {
        this.etatIphone = etatIphone;
    }
//    public Iphone miseAJourIphoneSuiteClotureAffectation() {
//
//        if (this.etatIphone == EtatIphoneEnum.AFFECTE){
//            this.etatIphone = EtatIphoneEnum.DISPONIBLE;
//        }
//        return this;
//    }

    public Iphone miseAJourIphoneSuiteClotureAffectation(String motifFin) {

        if (this.etatIphone == EtatIphoneEnum.AFFECTE) {
            if (motifFin == "RESTITUE" || motifFin == "SUPPRIME") {
                this.etatIphone = EtatIphoneEnum.DISPONIBLE;
            }

            if (motifFin == "VOLE") {
                this.etatIphone = EtatIphoneEnum.VOLE;
            }
            if (motifFin == "CASSE") {
                this.etatIphone = EtatIphoneEnum.CASSE;
            }
            if (motifFin == "PERDU") {
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
