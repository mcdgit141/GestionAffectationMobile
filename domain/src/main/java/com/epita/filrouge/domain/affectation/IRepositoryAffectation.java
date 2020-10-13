package com.epita.filrouge.domain.affectation;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public interface IRepositoryAffectation {

    void affecter(Affectation affectationACreer);

    Affectation supprimerAffectation(Affectation affectationASupprimer);

    List<Affectation> rechercheAffectationByUid(String collaborateurUid);

    List<Affectation> rechercheAffectationAvecFiltres(FiltresAffectation filtresAffectation);
    AbstractMap.Entry<List<Integer>, List<Affectation>> rechercheAffectationAvecFiltres2(FiltresAffectation filtresAffectation);

    Affectation chercheAffectationParNumeroAffectation(Long numeroAffectation);

    void miseAjourAffectation(Affectation affectation);
}