package com.epita.filrouge.expositions.collaborateur;

import com.epita.filrouge.expositions.affectation.AffectationFullDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Validated
@ApiModel(value = "Collaborateur DTO", description = "Données colaborateurs")
public class CollaborateurDTO {

    @JsonProperty
    @ApiModelProperty(example = "405809", required = true, value = "Uid du Collaborateur")
    @NotNull(message = "L'UID du collaborateur ne peut etre vide")
    @Pattern(regexp = "^([a-zA-Z0-9]{6})$" , message = "L'UID du collaborateur n'est pas valide")
    @Size(max = 6)
    private String uid;

    @JsonProperty
    private String nom;

    @JsonProperty
    private String prenom;

    @JsonProperty
    private String numeroLigne;

    @JsonProperty
    private UoDTO uo;

    public CollaborateurDTO(String uid, String nom, String prenom, String numeroLigne, UoDTO uo) {
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroLigne = numeroLigne;
        this.uo = uo;
    }

    public CollaborateurDTO() {
    }

    public String getUid() {
        return uid;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumeroLigne() {
        return numeroLigne;
    }

    public UoDTO getUo() {
        return uo;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public static class UoDTO {

        private String codeUo;
        private String fonctionRattachement;
        private String codeUoParent;
        private String nomUsageUo;
        private String nomResponsableUo;
        private SiteExerciceDTO siteExercice;

        public String getCodeUo() {
            return codeUo;
        }

        public void setCodeUo(String codeUo) {
            this.codeUo = codeUo;
        }

        public String getFonctionRattachement() {
            return fonctionRattachement;
        }

        public void setFonctionRattachement(String fonctionRattachement) {
            this.fonctionRattachement = fonctionRattachement;
        }

        public String getCodeUoParent() {
            return codeUoParent;
        }

        public void setCodeUoParent(String codeUoParent) {
            this.codeUoParent = codeUoParent;
        }

        public String getNomUsageUo() {
            return nomUsageUo;
        }

        public void setNomUsageUo(String nomUsageUo) {
            this.nomUsageUo = nomUsageUo;
        }

        public String getNomResponsableUo() {
            return nomResponsableUo;
        }

        public void setNomResponsableUo(String nomResponsableUo) {
            this.nomResponsableUo = nomResponsableUo;
        }

        public SiteExerciceDTO getSiteExercice() {
            return siteExercice;
        }

        public void setSiteExercice(SiteExerciceDTO siteExercice) {
            this.siteExercice = siteExercice;
        }
    }


    public static class SiteExerciceDTO {

        private String codeSite;
        private String nomSite;
        private String adressePostale1;
        private String codePostal;
        private String ville;
        private String pays;
        private LocalDate dateCreation;
        private LocalDate dateCloture;

        public String getCodeSite() {
            return codeSite;
        }

        public void setCodeSite(String codeSite) {
            this.codeSite = codeSite;
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
}
