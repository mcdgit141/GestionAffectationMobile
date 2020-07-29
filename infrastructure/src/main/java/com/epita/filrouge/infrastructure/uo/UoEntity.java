package com.epita.filrouge.infrastructure.uo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uoId;

    private String codeUo;
    private String fonctionRattachement;
    private String codeUoRattachementHierarchique;
    private String nomUsageUo;
    private String nomResponsableUo;

    public UoEntity() {

    }

    public Long getUoId() {
        return uoId;
    }

    public void setUoId(Long uoId) {
        this.uoId = uoId;
    }

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

    public String getCodeUoRattachementHierarchique() {
        return codeUoRattachementHierarchique;
    }

    public void setCodeUoRattachementHierarchique(String codeUoRattachementHierarchique) {
        this.codeUoRattachementHierarchique = codeUoRattachementHierarchique;
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
}
