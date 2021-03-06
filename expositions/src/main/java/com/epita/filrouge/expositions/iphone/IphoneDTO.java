package com.epita.filrouge.expositions.iphone;

import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(value = "Iphone", description = "Données Iphone")
@Validated
public class IphoneDTO {



    @JsonProperty
    @ApiModelProperty(example = "10504", required = true, value = "numéro de série de l'iphone")
    @NotNull(message = "Le numéro de série doit être renseigné")
    @Pattern(regexp = "^([ A-Za-z0-9]{1,30})$")
    @Size(max = 30)
    private String numeroSerie;

    @JsonProperty
    private double prixIphone;

    @JsonProperty
    private ModeleIphoneDTO modeleIphoneDTO;

    @JsonProperty
    private EtatIphoneEnum etatIphone;

    public IphoneDTO() {

    }



    public IphoneDTO(@NotNull(message = "Le numéro de série doit être renseigné") @Pattern(regexp = "^([ A-Za-z0-9]{1,30})$") @Size(max = 30) String numeroSerie, double prixIphone, ModeleIphoneDTO modeleIphoneDTO, EtatIphoneEnum etatIphone) {
        this.numeroSerie = numeroSerie;
        this.prixIphone = prixIphone;
        this.modeleIphoneDTO = modeleIphoneDTO;
        this.etatIphone = etatIphone;
    }


    public String getNumeroSerie() {
        return numeroSerie;
    }


    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }



    public static class  ModeleIphoneDTO {

        private Long modeleID;
        private String nomModele;

        public ModeleIphoneDTO() {
        }

        public ModeleIphoneDTO(Long modeleID, String nomModele) {
            this.modeleID = modeleID;
            this.nomModele = nomModele;

        }

        public Long getModeleID() {
            return modeleID;
        }

        public String getNomModele() {
            return nomModele;
        }

    }

}
