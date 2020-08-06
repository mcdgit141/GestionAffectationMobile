package com.epita.filrouge.application.utilisateur;

import com.epita.filrouge.domain.utilisateur.Utilisateur;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;

public interface IUtilisateurManagement {

    void enregistrerUtilisateur(String uid, UtilisateurRoleEnum profil);

    Utilisateur rechercheUtilisateur(String login);
}
