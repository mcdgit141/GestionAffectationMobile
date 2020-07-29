package com.epita.filrouge.domain.site;

import java.time.LocalDate;

public class SiteExercice {

        private String codeSite;
        private String nomSite;
        private String adressePostale1;
        private String codePostal;
        private String ville;
        private String pays;
        private LocalDate dateCreation;
        private LocalDate dateCloture;

    public SiteExercice(String codeSite, String nomSite, String adressePostale1, String codePostal, String ville, String pays, LocalDate dateCreation) {
        this.codeSite = codeSite;
        this.nomSite = nomSite;
        this.adressePostale1 = adressePostale1;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
        this.dateCreation = dateCreation;
    }

    public String getCodeSite() {
        return codeSite;
    }

    public String getNomSite() {
        return nomSite;
    }

    public String getAdressePostale1() {
        return adressePostale1;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getVille() {
        return ville;
    }

    public String getPays() {
        return pays;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public LocalDate getDateCloture() {
        return dateCloture;
    }
}
