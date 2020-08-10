package com.epita.filrouge.application.utilisateur;

import com.epita.filrouge.application.collaborateur.ICollaborateurManagement;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.exception.AllReadyExistException;
import com.epita.filrouge.domain.exception.BadRequestException;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.utilisateur.IRepositoryUtilisateur;
import com.epita.filrouge.domain.utilisateur.Utilisateur;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurManagementImpl implements IUtilisateurManagement{

    @Autowired
    private ICollaborateurManagement monCollaborateurManagement;

    @Autowired
    private IRepositoryUtilisateur repositoryUtilisateur;

    @Override
    public void enregistrerUtilisateur(String uid, String profil) {
        Collaborateur monCollaborateur = monCollaborateurManagement.findByUid(uid);
        UtilisateurRoleEnum roleUtilisateurACreer;
        switch (profil.toUpperCase()){
            case "ADMIN":
                roleUtilisateurACreer = UtilisateurRoleEnum.ROLE_ADMIN;
                break;
            case "TYPE1":
                roleUtilisateurACreer = UtilisateurRoleEnum.ROLE_TYPE1;
                break;
            case "TYPE2":
                roleUtilisateurACreer = UtilisateurRoleEnum.ROLE_TYPE2;
                break;
            default:
                throw new BadRequestException("role utilisateur transmis inconnu");
        }

        Utilisateur utilisateurACreer = new Utilisateur(monCollaborateur.getUid(),monCollaborateur.getNom(), monCollaborateur.getPrenom(), roleUtilisateurACreer);
        Utilisateur utilisateurExistant = repositoryUtilisateur.rechercherUser(utilisateurACreer.getLogin());
        if (utilisateurExistant == null){
            repositoryUtilisateur.creerUser(utilisateurACreer);
        } else {
            throw new AllReadyExistException("Un Utilisateur existe déjà pour cet uid");
        }

    }

    @Override
    public void supprimerUtilisateur(String login) {
        Utilisateur utilisateurASupprimer = repositoryUtilisateur.rechercherUser(login);
        if (utilisateurASupprimer != null){
            repositoryUtilisateur.deleteUser(utilisateurASupprimer);
        } else {
            throw new NotFoundException("Aucun utilisateur existant avec ce login");
        }
    }
}
