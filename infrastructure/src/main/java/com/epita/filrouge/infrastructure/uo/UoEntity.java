package com.epita.filrouge.infrastructure.uo;

import com.epita.filrouge.infrastructure.site.SiteExerciceEntity;

import javax.persistence.*;

@Entity
//@Table(name ="Uo")
public class UoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uoId;

    @Column(unique = true)
    private String codeUo;
    private String fonctionRattachement;
    private String codeUoParent;
    private String nomUsageUo;
    private String nomResponsableUo;

    @OneToOne
    @JoinColumn ( name="siteId" )
    private SiteExerciceEntity siteExercice;

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

    public SiteExerciceEntity getSiteExercice() {
        return siteExercice;
    }

    public void setSiteExercice(SiteExerciceEntity siteExercice) {
        this.siteExercice = siteExercice;
    }

}