package com.epita.filrouge.expositions.affectation;

import io.swagger.annotations.ApiModel;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@ApiModel(value = "Meta et Affectation full DTO", description = "Données d'Affectation avec métadonnées (nombre d'enregistrements...")
@Validated
public class MetaAffectationDTO {
    MetaDTO metadata;
    List<AffectationFullDTO> datas;

    public List<AffectationFullDTO> getDatas() {
        return datas;
    }

    public void setDatas(List<AffectationFullDTO> datas) {
        this.datas = datas;
    }

    public MetaDTO getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaDTO metadata) {
        this.metadata = metadata;
    }



// -------------------------------
    public static class  MetaDTO {

        private Integer nombreTotalDEnregistrements;
        private Integer nombreDePages;
        private Integer numeroDeLaPageRetournee;
        private Integer nombreEnregistrementParPage;

        public MetaDTO() {
        }

        public MetaDTO(Integer nombreTotalDEnregistrements, Integer nombreDePages, Integer numeroDeLaPageRetournee, Integer nombreEnregistrementParPage) {
            this.nombreTotalDEnregistrements = nombreTotalDEnregistrements;
            this.nombreDePages = nombreDePages;
            this.numeroDeLaPageRetournee = numeroDeLaPageRetournee;
            this.nombreEnregistrementParPage = nombreEnregistrementParPage;
        }

        public Integer getNombreTotalDEnregistrements() {
            return nombreTotalDEnregistrements;
        }

        public void setNombreTotalDEnregistrements(Integer nombreTotalDEnregistrements) {
            this.nombreTotalDEnregistrements = nombreTotalDEnregistrements;
        }

        public Integer getNombreDePages() {
            return nombreDePages;
        }

        public void setNombreDePages(Integer nombreDePages) {
            this.nombreDePages = nombreDePages;
        }

        public Integer getNumeroDeLaPageRetournee() {
            return numeroDeLaPageRetournee;
        }

        public void setNumeroDeLaPageRetournee(Integer numeroDeLaPageRetournee) {
            this.numeroDeLaPageRetournee = numeroDeLaPageRetournee;
        }

        public Integer getNombreEnregistrementParPage() {
            return nombreEnregistrementParPage;
        }

        public void setNombreEnregistrementParPage(Integer nombreEnregistrementParPage) {
            this.nombreEnregistrementParPage = nombreEnregistrementParPage;
        }
    }
}
