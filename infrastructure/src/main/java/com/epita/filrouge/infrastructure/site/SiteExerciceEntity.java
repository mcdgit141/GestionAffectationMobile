package com.epita.filrouge.infrastructure.site;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
//@Table(name ="SiteExercice")
public class SiteExerciceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long siteId;

    @Column(unique = true)
    private String codeSite;
    private String nomSite;
    private String adressePostale1;
    private String codePostal;
    private String ville;
    private String pays;
    private LocalDate dateCreation;
    private LocalDate dateCloture;

    public SiteExerciceEntity() {

    }

    public String getCodeSite() {
        return codeSite;
    }

    public void setCodeSite(String codeSite) {
        this.codeSite = codeSite;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getNomSite() {
        return nomSite;
    }

    public void setNomSite(String nomSite) {
        this.nomSite = nomSite;
    }

    public String getAdressePostale1() {
        return adressePostale1;
    }

    public void setAdressePostale1(String adressePostale1) {
        this.adressePostale1 = adressePostale1;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(LocalDate dateCloture) {
        this.dateCloture = dateCloture;
    }
}
