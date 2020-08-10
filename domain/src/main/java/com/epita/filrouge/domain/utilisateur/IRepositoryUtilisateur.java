package com.epita.filrouge.domain.utilisateur;

import java.util.List;

public interface IRepositoryUtilisateur {
    void creerUser(Utilisateur utilisateur);

     Utilisateur rechercherUser(String login);

    void deleteUser(Utilisateur utilisateurASupprimer);

    Utilisateur rechercherUserParUid(String uid);

}

