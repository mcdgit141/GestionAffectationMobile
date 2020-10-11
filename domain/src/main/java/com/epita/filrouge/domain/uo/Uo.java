package com.epita.filrouge.domain.uo;

import com.epita.filrouge.domain.site.SiteExercice;

public class Uo {

    private String codeUo;
    private String fonctionRattachement;
    private String codeUoParent;
    private String nomUsageUo;
    private String nomResponsableUo;
    private SiteExercice siteExercice;

    public Uo(String codeUo, String fonctionRattachement, String codeUoParent, String nomUsageUo, String nomResponsableUo) {
        this.codeUo = codeUo;
        this.fonctionRattachement = fonctionRattachement;
        this.codeUoParent = codeUoParent;
        this.nomUsageUo = nomUsageUo;
        this.nomResponsableUo = nomResponsableUo;
    }

    public Uo(String codeUo, String fonctionRattachement, String codeUoParent, String nomUsageUo, String nomResponsableUo, SiteExercice siteExercice) {
        this.codeUo = codeUo;
        this.fonctionRattachement = fonctionRattachement;
        this.codeUoParent = codeUoParent;
        this.nomUsageUo = nomUsageUo;
        this.nomResponsableUo = nomResponsableUo;
        this.siteExercice = siteExercice;
    }

    public String getCodeUo() {
        return codeUo;
    }

    public String getFonctionRattachement() {
        return fonctionRattachement;
    }

    public String getCodeUoParent() {
        return codeUoParent;
    }

    public String getNomUsageUo() {
        return nomUsageUo;
    }

    public String getNomResponsableUo() {
        return nomResponsableUo;
    }

    public SiteExercice getSiteExercice() {
        return siteExercice;
    }

    public void setSiteExercice(SiteExercice siteExercice) {
        this.siteExercice = siteExercice;
    }
}
