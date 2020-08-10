package com.epita.filrouge.infrastructure.utilisateur;

import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.utilisateur.IRepositoryUtilisateur;
import com.epita.filrouge.domain.utilisateur.Utilisateur;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositoryUtilisateurImpl implements IRepositoryUtilisateur {

    @Autowired
    private IRepositoryJpaUtilisateur userJpaRepository;

    @Override
    public Utilisateur rechercherUserParUid(String uid) {
        UtilisateurEntity monUtilisateurEntity = userJpaRepository.findByUid(uid);
        if (monUtilisateurEntity != null) {
            return utilisateurMapper.mapToDomain(monUtilisateurEntity);
        } else {
            return  null;
        }
    }

    @Autowired
    private UtilisateurMapper utilisateurMapper;


    @Override
    public void deleteUser(Utilisateur utilisateurASupprimer) {
        UtilisateurEntity monUtilisateurEntityASupprimer = userJpaRepository.findByLogin(utilisateurASupprimer.getLogin());
        if (monUtilisateurEntityASupprimer != null) {
            userJpaRepository.delete(monUtilisateurEntityASupprimer);
        } else {
            throw new NotFoundException("UtilisateurEntity à supprimer inexistant");
        }
    }

    @Override
    public void creerUser(Utilisateur utilisateur) {
        UtilisateurEntity monUtilisateurEntity = utilisateurMapper.mapToEntity(utilisateur);

        userJpaRepository.save(monUtilisateurEntity);
    }

    @Override
    public Utilisateur rechercherUser(String login) {
        UtilisateurEntity monUtilisateurEntity = userJpaRepository.findByLogin(login);
        if (monUtilisateurEntity != null) {
            return utilisateurMapper.mapToDomain(monUtilisateurEntity);
        } else {
            return  null;
        }
    }

}
