package com.epita.filrouge.application.affectation;


import com.epita.filrouge.domain.affectation.Affectation;

import java.time.LocalDate;

public interface IAffectationManagement {

    public Affectation save(String collaborateurUid, String iPhoneNumeroSerie, LocalDate dateAffectation, String numeroLigne, String commentaire);
}
