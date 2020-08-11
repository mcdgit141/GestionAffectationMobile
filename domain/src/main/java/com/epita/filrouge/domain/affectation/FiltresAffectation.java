package com.epita.filrouge.domain.affectation;

import java.time.LocalDate;

public class FiltresAffectation {

    String uid;
    String nom;
    String codeUo;
    String nomUsageUo;
    String nomSite;
    String numeroLigneCollaborateur;
    String nomModeleIphone;
    LocalDate dateRenouvMin;
    LocalDate dateRenouvMax;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodeUo() {
        return codeUo;
    }

    public void setCodeUo(String codeUo) {
        this.codeUo = codeUo;
    }

    public String getNomUsageUo() {
        return nomUsageUo;
    }

    public void setNomUsageUo(String nomUsageUo) {
        this.nomUsageUo = nomUsageUo;
    }

    public String getNomSite() {
        return nomSite;
    }

    public void setNomSite(String nomSite) {
        this.nomSite = nomSite;
    }

    public String getNumeroLigneCollaborateur() {
        return numeroLigneCollaborateur;
    }

    public void setNumeroLigneCollaborateur(String numeroLigneCollaborateur) {
        this.numeroLigneCollaborateur = numeroLigneCollaborateur;
    }

    public String getNomModeleIphone() {
        return nomModeleIphone;
    }

    public void setNomModeleIphone(String nomModeleIphone) {
        this.nomModeleIphone = nomModeleIphone;
    }

    public LocalDate getDateRenouvMin() {
        return dateRenouvMin;
    }

    public void setDateRenouvMin(LocalDate dateRenouvMin) {
        this.dateRenouvMin = dateRenouvMin;
    }

    public LocalDate getDateRenouvMax() {
        return dateRenouvMax;
    }

    public void setDateRenouvMax(LocalDate dateRenouvMax) {
        this.dateRenouvMax = dateRenouvMax;
    }
}
