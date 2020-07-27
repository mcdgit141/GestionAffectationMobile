package com.epita.filrouge.domain.affectation;

import com.epita.filrouge.domain.exception.NotFoundException;

import java.util.List;

public interface IRepositoryAffectation {

    public void affecter(Affectation affectationACreer);

    List<Affectation> rechercheAffectationByUid(String collaborateurUid);
}
