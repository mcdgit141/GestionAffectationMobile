package com.epita.filrouge.application.affectation;


import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.FiltresAffectation;

import java.time.LocalDate;
import java.util.List;

public interface IAffectationManagement {

    Affectation creerAffectation(String collaborateurUid, String iPhoneNumeroSerie, LocalDate dateAffectation, String numeroLigne, String commentaire);

    List<Affectation> listerAffectation();


//    public <T> List<Affectation> listerAffection(T criteresDeFiltre );   // l'objet T est une liste contenant les critères de filtre transmis par le front

    List<Affectation> listerAffectation(FiltresAffectation filtresAffectation);



    List<Affectation> listerAffectation(String uid, String nom, String codeUo, String nomUsageUo, String nomSite,
                                      String numeroLigneCollaborateur,String nomModeleIphone, LocalDate dateRenouvMin, LocalDate dateRenouvMax );


}
