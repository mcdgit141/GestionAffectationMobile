package com.epita.filrouge.domain.uo;

public class Uo {

    private String codeUo;
    private String fonctionRattachement;
    private String codeUoRattachementHierarchique;
    private String nomUsageUo;
    private String nomResponsableUo;

    public Uo(String codeUo, String fonctionRattachement, String codeUoRattachementHierarchique, String nomUsageUo, String nomResponsableUo) {
        this.codeUo = codeUo;
        this.fonctionRattachement = fonctionRattachement;
        this.codeUoRattachementHierarchique = codeUoRattachementHierarchique;
        this.nomUsageUo = nomUsageUo;
        this.nomResponsableUo = nomResponsableUo;
    }

    public String getCodeUo() {
        return codeUo;
    }

    public String getFonctionRattachement() {
        return fonctionRattachement;
    }

    public String getCodeUoRattachementHierarchique() {
        return codeUoRattachementHierarchique;
    }

    public String getNomUsageUo() {
        return nomUsageUo;
    }

    public String getNomResponsableUo() {
        return nomResponsableUo;
    }
}
