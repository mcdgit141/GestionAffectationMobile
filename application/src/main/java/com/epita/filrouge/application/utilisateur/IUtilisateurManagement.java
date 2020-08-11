package com.epita.filrouge.application.utilisateur;

import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.utilisateur.Utilisateur;

public interface IUtilisateurManagement {

    void enregistrerUtilisateur(String uid, String profil);

    void supprimerUtilisateur(String uid) throws NotFoundException;

}
