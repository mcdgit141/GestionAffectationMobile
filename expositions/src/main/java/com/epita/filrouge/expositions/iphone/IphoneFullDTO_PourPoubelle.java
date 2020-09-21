package com.epita.filrouge.expositions.iphone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(value = "Iphone", description = "Données Iphone")
@Validated
public class IphoneFullDTO_PourPoubelle {

    @ApiModelProperty(example = "1L", required = true, value = "Iphone Description champ IphoneId généré en automatique par la base de données")
    private Long iphoneId;

    @ApiModelProperty(example = "10504", required = true, value = "numéro de série de l'iphone")
    @NotNull(message = "Le numéro de série doit être renseigné")
    @Pattern(regexp = "^([ A-Za-z0-9]{1,30})$")
    @Size(max = 30)
    private String numeroSerie;

    public IphoneFullDTO_PourPoubelle() {

    }

    public IphoneFullDTO_PourPoubelle(Long iphoneId, @NotNull @Pattern(regexp = "^([ A-Za-z0-9]{1,30})$") @Size(max = 30) String numeroSerie) {
        this.iphoneId = iphoneId;
        this.numeroSerie = numeroSerie;
    }

    public Long getIphoneId() {
        return iphoneId;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setIphoneId(Long iphoneId) {
        this.iphoneId = iphoneId;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    }
