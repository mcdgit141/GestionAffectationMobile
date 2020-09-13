package com.epita.filrouge.expositions.affectation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.time.LocalDate;

@ApiModel(value = "Affectation DTO", description = "Données d'Affectation ")
@Validated
public class AffectationDTO {

    @ApiModelProperty(example = "256541868779997", required = true, value = "numero d'affectation généré aléatoirement")
    private Long numeroAffectation;

    @ApiModelProperty(example = "405809", required = true, value = "Uid du Collaborateur")
    @NotNull(message = "L'UID du collaborateur ne peut etre vide")
    @Pattern(regexp = "^([a-zA-Z0-9]{6})$" , message = "L'UID du collaborateur n'est pas valide")
    @Size(max = 6)
    private String collaborateurUid;

    @ApiModelProperty(example = "10504", required = true, value = "numéro de série de l'iphone")
    @NotNull
    @Pattern(regexp = "^([ A-Za-z0-9]{1,30})$")
    @Size(max = 30)
    private String iphoneNumeroSerie;

    @ApiModelProperty(example = "2020-08-18", required = true, value = "Date de l'affectation")
//    @Future
    private LocalDate affectationDate;

    @ApiModelProperty(example = "0655896574", required = true, value = "nuémro de ligne du Collaborateur")
    @NotNull
    @Pattern(regexp = "(\\d{10})$")
    @Size(max = 10)
    private String collaborateurNumeroLigne;

    @ApiModelProperty(example = "Attribution d'un iphone pour les astreintes", required = true, value = "commentaire à préciser lors d'une affectation")
    @NotNull
    @Pattern(regexp = "^([ a-zA-Z0-9]{1,200})$")
    @Size(max = 200)
    private String affectationCommentaire;

    @ApiModelProperty(example = "VOLE", required = true, value = "peut prendre 4 valeurs: VOLE, CASSE, PERDU et RESTITUE")
//    @NotNull(message = "motif doit etre renseigne")
//    @Pattern(regexp = "^([A-Z]{1,8})$")
//    @Size(max = 8)
    private String motifFin;

    @ApiModelProperty(example = "2020-08-18", required = true, value = "Date de fin de l'affectation lors de la clôture")
//    @Future
    private LocalDate dateFin;

    public AffectationDTO() {
    }

    public String getCollaborateurUid() {
        return collaborateurUid;
    }

    public void setCollaborateurUid(String collaborateurUid) {
        this.collaborateurUid = collaborateurUid;
    }

    public String getIphoneNumeroSerie() {
        return iphoneNumeroSerie;
    }

    public void setIphoneNumeroSerie(String iphoneNumeroSerie) {
        this.iphoneNumeroSerie = iphoneNumeroSerie;
    }

    public LocalDate getAffectationDate() {
        return affectationDate;
    }

    public void setAffectationDate(LocalDate affectationDate) {
        this.affectationDate = affectationDate;
    }

    public String getCollaborateurNumeroLigne() {
        return collaborateurNumeroLigne;
    }

    public void setCollaborateurNumeroLigne(String collaborateurNumeroLigne) {
        this.collaborateurNumeroLigne = collaborateurNumeroLigne;
    }

    public String getAffectationCommentaire() {
        return affectationCommentaire;
    }

    public void setAffectationCommentaire(String affectationCommentaire) {
        this.affectationCommentaire = affectationCommentaire;
    }

    public Long getNumeroAffectation() {
        return numeroAffectation;
    }

    public void setNumeroAffectation(Long numeroAffectation) {
        this.numeroAffectation = numeroAffectation;
    }

    public String getMotifFin() {
        return motifFin;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setMotifFin(String motifFin) {
        this.motifFin = motifFin;
    }

    @Override
    public String toString() {
        return "\n" + "AffectationDTO{" +
                "\n" + "numeroAffectation=" + numeroAffectation +
                "\n" + ", collaborateurUid='" + collaborateurUid + '\'' +
                "\n" + ", iphoneNumeroSerie='" + iphoneNumeroSerie + '\'' +
                "\n" + ", affectationDate=" + affectationDate +
                "\n" + ", collaborateurNumeroLigne='" + collaborateurNumeroLigne + '\'' +
                "\n" + ", affectationCommentaire='" + affectationCommentaire + '\'' +
                "\n" + ", motifFin='" + motifFin + '\'' +
                "\n" + ", dateFin=" + dateFin +
                "\n" + '}';
    }
}