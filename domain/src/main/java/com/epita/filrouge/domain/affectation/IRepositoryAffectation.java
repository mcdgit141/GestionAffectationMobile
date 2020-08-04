package com.epita.filrouge.domain.affectation;

import java.time.LocalDate;
import java.util.List;

public interface IRepositoryAffectation {

    void affecter(Affectation affectationACreer);

    List<Affectation> listerAffectation();

    List<Affectation> rechercheAffectationByUid(String collaborateurUid);

    List<Affectation> rechercheAffectationAvecFiltres(String uid, String nomCollaborateur, String codeUo, String nomUsageUo, String nomSite,
                                                      String numeroLigneCollaborateur, String nomModeleIphone,
                                                      LocalDate dateRenouvMin, LocalDate dateRenouvMax);
}