package com.epita.filrouge.domain.utilisateur;

import com.epita.filrouge.domain.exception.NotFoundException;

import java.util.List;

public interface IRepositoryUtilisateur {
    void creerUser(Utilisateur utilisateur);

     Utilisateur rechercherUser(String login);

    void supprimerUser(Utilisateur utilisateurASupprimer);

    Utilisateur rechercherUserParUid(String uid);

    void modifierUtilisateur(Utilisateur utilisateurAModifier);

    void enregistrerUtilisateur(Utilisateur utilisateur);
}

