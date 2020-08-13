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
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurManagementImpl implements IUtilisateurManagement{

    @Autowired
    private ICollaborateurManagement monCollaborateurManagement;

    @Autowired
    private IRepositoryUtilisateur repositoryUtilisateur;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public Utilisateur rechercherUtilisateur(String uid) throws NotFoundException {
        return repositoryUtilisateur.rechercherUserParUid(uid);
    }

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
        try {
            Utilisateur utilisateurExistant = repositoryUtilisateur.rechercherUserParUid(uid);
            if (utilisateurExistant != null) {
                throw new AllReadyExistException("Un Utilisateur existe déjà avec l'uid : " + uid);
            }
        } catch (NotFoundException ex) {
            repositoryUtilisateur.enregistrerUtilisateur(utilisateurACreer);
        }


    }

    @Override
    public void modifierMdpUtilisateur(String uid, String mdp) throws NotFoundException  {
        Utilisateur utilisateurAModifier = repositoryUtilisateur.rechercherUserParUid(uid);
        utilisateurAModifier.setPassword(passwordEncoder.encode(mdp));

//        repositoryUtilisateur.modifierUtilisateur(utilisateurAModifier);
        repositoryUtilisateur.enregistrerUtilisateur(utilisateurAModifier);

    }

    @Override
    public void supprimerUtilisateur(String uid) throws NotFoundException {
        Utilisateur utilisateurASupprimer = repositoryUtilisateur.rechercherUserParUid(uid);
        repositoryUtilisateur.supprimerUser(utilisateurASupprimer);
    }
}
