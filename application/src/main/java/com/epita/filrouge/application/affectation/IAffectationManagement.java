package com.epita.filrouge.application.affectation;


import java.time.LocalDate;

public interface IAffectationManagement {

    public void save(String collaborateurUiD, String iPhoneNumeroSerie, LocalDate dateAffectation, String numeroLigne, String commentaire);
}
