package com.epita.filrouge.domain.collaborateur;

import com.epita.filrouge.domain.exception.NotFoundTransverseException;

public interface IRepositoryCollaborateur {

    public Collaborateur findByUid (String uid) throws NotFoundTransverseException;

    public Collaborateur findByNumeroLigne (String numeroLigne);

    public void miseAJourCollaborateur(Collaborateur collaborateur, String numLigne);


}
