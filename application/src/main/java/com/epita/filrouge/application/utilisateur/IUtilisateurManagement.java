package com.epita.filrouge.application.utilisateur;

import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.utilisateur.Utilisateur;

public interface IUtilisateurManagement {

    Utilisateur enregistrerUtilisateur(String uid, String profil);

    void supprimerUtilisateur(String uid) throws NotFoundException;

    void modifierMdpUtilisateur(String login, String mdp);

    Utilisateur rechercherUtilisateur(String uid) throws NotFoundException;
}
