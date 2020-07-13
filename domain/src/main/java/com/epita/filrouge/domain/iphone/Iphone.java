package com.epita.filrouge.domain.iphone;

import com.epita.filrouge.domain.affectation.Affectation;

import java.util.ArrayList;
import java.util.List;

public class Iphone {

    private String numeroSerie;
    private double prixIphone;

    private ModeleIphone modeleIphone;

    private EtatIphoneEnum etatIphone;

    private List<Affectation> affectationIphone;

    public Iphone(String numeroSerie, double prixIphone, ModeleIphone modeleIphone, EtatIphoneEnum etatIphone) {
        this.numeroSerie = numeroSerie;
        this.prixIphone = prixIphone;
        this.modeleIphone = modeleIphone;
        this.etatIphone = etatIphone;
        this.affectationIphone = new ArrayList<Affectation>();
    }

    public Iphone() {
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

   public EtatIphoneEnum getEtatIphone() {
        return etatIphone;
    }

    public List<Affectation> getAffectationIphone() {
        return affectationIphone;
    }

    public double getPrixIphone() {
        return prixIphone;
    }

    public ModeleIphone getModeleIphone() {
        return modeleIphone;
    }
}
