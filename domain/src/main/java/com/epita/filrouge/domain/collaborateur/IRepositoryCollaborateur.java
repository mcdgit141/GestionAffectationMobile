package com.epita.filrouge.domain.collaborateur;

import com.epita.filrouge.domain.exception.NotFoundException;

public interface IRepositoryCollaborateur {

    Collaborateur findByUid (String uid) throws NotFoundException;

    Collaborateur findByNumeroLigne (String numeroLigne) throws NotFoundException;

    void miseAJourCollaborateur(Collaborateur collaborateur, String numLigne);

}
