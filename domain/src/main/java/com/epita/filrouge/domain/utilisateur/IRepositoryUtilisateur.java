package com.epita.filrouge.domain.utilisateur;

import com.epita.filrouge.domain.exception.NotFoundException;

import java.util.List;

public interface IRepositoryUtilisateur {

     Utilisateur rechercherUserParLogin(String login);

    void supprimerUser(Utilisateur utilisateurASupprimer);

    Utilisateur rechercherUserParUid(String uid);

    Utilisateur enregistrerUtilisateur(Utilisateur utilisateur);
}

