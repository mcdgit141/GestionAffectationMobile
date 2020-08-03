package com.epita.filrouge.domain.affectation;

import com.epita.filrouge.domain.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface IRepositoryAffectation {

    public void affecter(Affectation affectationACreer);

    List<Affectation> listerAffectation();

    List<Affectation> rechercheAffectationAvecFiltres(String uid, String nom, String codeUo, String nomUsageUo, String nomSite, String numeroLigneCollaborateur, String nomModeleIphone, LocalDate dateRenouvMin, LocalDate dateRenouvMax);

//    List<Affectation> rechercheAffectationByUid(String collaborateurUid);
}
