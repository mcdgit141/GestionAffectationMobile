package com.epita.filrouge.domain.affectation;

import java.time.LocalDate;
import java.util.List;

public interface IRepositoryAffectation {

    void affecter(Affectation affectationACreer);

    void supprimerAffectation(Affectation affectationASupprimer);

    List<Affectation> rechercheAffectationByUid(String collaborateurUid);

    List<Affectation> rechercheAffectationAvecFiltres(FiltresAffectation filtresAffectation);
}