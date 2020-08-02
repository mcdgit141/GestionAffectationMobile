package com.epita.filrouge.domain.collaborateur;

import com.epita.filrouge.domain.exception.NotFoundException;

public interface IRepositoryCollaborateur {

    public Collaborateur findByUid (String uid) throws NotFoundException;

    public Collaborateur findByNumeroLigne (String numeroLigne) throws NotFoundException;

    public void miseAJourCollaborateur(Collaborateur collaborateur, String numLigne);


}
