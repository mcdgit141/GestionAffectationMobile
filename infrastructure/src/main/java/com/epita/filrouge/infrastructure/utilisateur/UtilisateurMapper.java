package com.epita.filrouge.infrastructure.utilisateur;

import com.epita.filrouge.domain.utilisateur.Utilisateur;

public class UtilisateurMapper {

    public static UtilisateurEntity mapToInfra(Utilisateur utilisateur){
        UtilisateurEntity monUtilisateurEntity = new UtilisateurEntity();
        monUtilisateurEntity.setLogin(utilisateur.getLogin());
        monUtilisateurEntity.setNom(utilisateur.getNom());
        monUtilisateurEntity.setPrenom(utilisateur.getPrenom());
        monUtilisateurEntity.setPassword(utilisateur.getPassword());

        return monUtilisateurEntity;
    }

    public static Utilisateur mapToDomain(UtilisateurEntity utilisateurEntity) {
        Utilisateur monUtilisateur = new Utilisateur(utilisateurEntity.getUid(), utilisateurEntity.getNom(), utilisateurEntity.getPrenom());
        monUtilisateur.setPassword(utilisateurEntity.getPassword());
        monUtilisateur.setUserRole(utilisateurEntity.getUserRole());

        return monUtilisateur;
    }
}
