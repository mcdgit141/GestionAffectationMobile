package com.epita.filrouge.infrastructure.utilisateur;

import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.utilisateur.IRepositoryUtilisateur;
import com.epita.filrouge.domain.utilisateur.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryUtilisateurImpl implements IRepositoryUtilisateur {

    @Autowired
    private IRepositoryJpaUtilisateur userJpaRepository;

    @Override
    public Utilisateur rechercherUserParUid(String uid) {
        UtilisateurEntity monUtilisateurEntity = userJpaRepository.findByUid(uid);
        if (monUtilisateurEntity != null) {
            return utilisateurEntityMapper.mapToDomain(monUtilisateurEntity);
        } else {
            throw new NotFoundException("Aucun utilisateur existant avec cet uid");
        }
    }

    @Override
    public void enregistrerUtilisateur(Utilisateur utilisateur) {
        UtilisateurEntity utilisateurEntityAEnregistrer = utilisateurEntityMapper.mapToEntity(utilisateur);

        UtilisateurEntity utilisateurEntityExistant = userJpaRepository.findByUid(utilisateur.getUid());
        if (utilisateurEntityExistant != null) {
            utilisateurEntityAEnregistrer.setId(utilisateurEntityExistant.getId());
        }
        userJpaRepository.save(utilisateurEntityAEnregistrer);
    }

    @Autowired
    private UtilisateurEntityMapper utilisateurEntityMapper;


    @Override
    public void modifierUtilisateur(Utilisateur utilisateurAModifier) {
        UtilisateurEntity utilisateurEntityAModifier = userJpaRepository.findByUid(utilisateurAModifier.getUid());
        utilisateurEntityAModifier.setUserRole(utilisateurAModifier.getUserRole());
        utilisateurEntityAModifier.setLogin(utilisateurAModifier.getLogin());
        utilisateurEntityAModifier.setPassword(utilisateurAModifier.getPassword());
        userJpaRepository.save(utilisateurEntityAModifier);
    }

    @Override
    public void supprimerUser(Utilisateur utilisateurASupprimer) {
        UtilisateurEntity monUtilisateurEntityASupprimer = userJpaRepository.findByLogin(utilisateurASupprimer.getLogin());
        if (monUtilisateurEntityASupprimer != null) {
            userJpaRepository.delete(monUtilisateurEntityASupprimer);
        } else {
            throw new NotFoundException("UtilisateurEntity à supprimer inexistant");
        }
    }

    @Override
    public void creerUser(Utilisateur utilisateur) {
        UtilisateurEntity monUtilisateurEntity = utilisateurEntityMapper.mapToEntity(utilisateur);

        userJpaRepository.save(monUtilisateurEntity);
    }

    @Override
    public Utilisateur rechercherUser(String login) {
        UtilisateurEntity monUtilisateurEntity = userJpaRepository.findByLogin(login);
        if (monUtilisateurEntity != null) {
            return utilisateurEntityMapper.mapToDomain(monUtilisateurEntity);
        } else {
            return  null;
        }
    }

}
