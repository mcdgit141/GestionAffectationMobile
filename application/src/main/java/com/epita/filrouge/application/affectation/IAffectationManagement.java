package com.epita.filrouge.application.affectation;


import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.FiltresAffectation;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;

public interface IAffectationManagement {

    Affectation creerAffectation(String collaborateurUid, String iPhoneNumeroSerie, LocalDate dateAffectation, String numeroLigne, String commentaire);

    List<Affectation> listerAffectation(FiltresAffectation filtresAffectation);
    AbstractMap.Entry<List<Integer>, List<Affectation>> listerAffectation2(FiltresAffectation filtresAffectation);

    void cloturerAffectation(Long numeroAffectation, String affectationCommentaire, String motifFin, LocalDate dateFin);

    Affectation supprimerAffectation(Long numeroAffectation);
}
