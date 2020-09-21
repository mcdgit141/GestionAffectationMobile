package com.epita.filrouge.expositions.collaborateur;

import com.epita.filrouge.domain.affectation.Affectation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel(value = "Collaborateur DTO", description = "Données du Colllaborateur")
@Validated
public class CollaborateurFullDTO {

    @ApiModelProperty(example = "405809", required = true, value = "Uid du Collaborateur")
    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9]{6})$")
    @Size(max = 6)
    private String uid;

    @ApiModelProperty(example = "DURAND", required = true, value = "nom de famille du Collaborateur")
    @NotNull
    @Pattern(regexp = "^([ A-Za-z0-9]{1,50})$")
    @Size(max = 50)
    private String nom;

    @ApiModelProperty(example = "Jean-Baptiste", required = true, value = "prénom du Collaborateur")
    @NotNull
    @Pattern(regexp = "^([ A-Za-z0-9]{1,30})$")
    @Size(max = 30)
    private String prenom;

    @ApiModelProperty(example = "0655896574", required = true, value = "nuémro de ligne du Collaborateur")
    @NotNull
    @Pattern(regexp = "(\\d{10})$")
    @Size(max = 10)
    private String numeroLigne;

//    private List<Affectation> affectationCollaborateur;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNumeroLigne(String numeroLigne) {
        this.numeroLigne = numeroLigne;
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

}
