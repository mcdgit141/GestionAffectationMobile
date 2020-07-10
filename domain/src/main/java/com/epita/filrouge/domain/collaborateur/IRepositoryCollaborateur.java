package com.epita.filrouge.domain.collaborateur;

public interface IRepositoryCollaborateur {

    public Collaborateur findByUid (String uid);

    public Collaborateur findByNumeroLigne (String numeroLigne);


}
