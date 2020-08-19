package com.epita.filrouge.expositions.iphone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(value = "Iphone", description = "Données Iphone")
@Validated
public class IphoneFullDTO {

    @ApiModelProperty(example = "1L", required = true, value = "Iphone Description champ IphoneId généré en automatique par la base de données")
    private Long iphoneId;

    @ApiModelProperty(example = "10504", required = true, value = "numéro de série de l'iphone")
    @NotNull
    @Pattern(regexp = "^([ A-Za-z0-9]{1,30})$")
    @Size(max = 30)
    private String numeroSerie;

    public IphoneFullDTO() {

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
