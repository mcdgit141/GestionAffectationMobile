package com.epita.filrouge.expositions.affectation;

import com.epita.filrouge.expositions.collaborateur.CollaborateurDTO;
import com.epita.filrouge.expositions.iphone.IphoneDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@ApiModel(value = "Affectation full DTO", description = "Données d'Affectation ")
@Validated
public class AffectationFullDTO {
    @JsonProperty
    private Long numeroAffectation;

    @JsonProperty
    @ApiModelProperty(example = "2020-08-18", required = true, value = "Date de l'affectation")
    private LocalDate dateAffectation;

    @JsonProperty
    private LocalDate dateRenouvellementPrevue;
    @JsonProperty
    private LocalDate dateFin;

    @JsonProperty
    @ApiModelProperty(example = "Attribution d'un iphone pour les astreintes", required = true, value = "commentaire à préciser lors d'une affectation")
    @NotNull(message = "Le commentaire est obligatoire")
    @Size(max = 200)
    private String commentaire;
    @JsonProperty
    private String motifFin;

    @Valid
    @JsonProperty
    private CollaborateurDTO collaborateur;

    @Valid
    @JsonProperty
    private IphoneDTO iphone;

    public AffectationFullDTO() {
    }


    public Long getNumeroAffectation() {
        return numeroAffectation;
    }

    public void setNumeroAffectation(Long numeroAffectation) {
        this.numeroAffectation = numeroAffectation;
    }

    public LocalDate getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(LocalDate dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public LocalDate getDateRenouvellementPrevue() {
        return dateRenouvellementPrevue;
    }

    public void setDateRenouvellementPrevue(LocalDate dateRenouvellementPrevue) {
        this.dateRenouvellementPrevue = dateRenouvellementPrevue;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getMotifFin() {
        return motifFin;
    }

    public void setMotifFin(String motifFin) {
        this.motifFin = motifFin;
    }

    public CollaborateurDTO getCollaborateur() {
        return collaborateur;
    }

    public void setCollaborateur(CollaborateurDTO collaborateur) {
        this.collaborateur = collaborateur;
    }

    public IphoneDTO getIphone() {
        return iphone;
    }

    public void setIphone(IphoneDTO iphoneFullDTO) {
        this.iphone = iphoneFullDTO;
    }

    @Override
    public String toString() {
        return "AffectationFullDTO{" +
                "numeroAffectation=" + numeroAffectation +
                ", dateAffectation=" + dateAffectation +
                ", dateRenouvellementPrevue=" + dateRenouvellementPrevue +
                ", dateFin=" + dateFin +
                ", commentaire='" + commentaire + '\'' +
                ", motifFin='" + motifFin + '\'' +
                ", collaborateur=" + collaborateur +
                ", iphone=" + iphone +
                '}';
    }
}
