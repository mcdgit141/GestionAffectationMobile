package com.epita.filrouge.domain.iphone;

import com.epita.filrouge.domain.affectation.Affectation;

import java.util.ArrayList;
import java.util.List;

public class Iphone {

    private String numeroSerie;
    private String numeroLigne;
    private ModeleEnum modele;
    private EtatIphoneEnum etatIphone;

    private List<Affectation> affectationIphone;

    public Iphone(String numeroSerie, String numeroLigne, ModeleEnum modele, EtatIphoneEnum etatIphone) {
        this.numeroSerie = numeroSerie;
        this.numeroLigne = numeroLigne;
        this.modele = modele;
        this.etatIphone = etatIphone;
        this.affectationIphone = new ArrayList<Affectation>();
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public String getNumeroLigne() {
        return numeroLigne;
    }

    public ModeleEnum getModele() {
        return modele;
    }

    public EtatIphoneEnum getEtatIphone() {
        return etatIphone;
    }

    public List<Affectation> getAffectationIphone() {
        return affectationIphone;
    }
}
