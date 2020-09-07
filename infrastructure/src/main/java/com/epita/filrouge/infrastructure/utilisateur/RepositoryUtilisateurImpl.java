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

    @Autowired
    private UtilisateurEntityMapper utilisateurEntityMapper;

    @Override
    public Utilisateur rechercherUserParUid(String uid) {
        UtilisateurEntity monUtilisateurEntity = userJpaRepository.findByCollaborateurLightUid(uid)
                .orElseThrow(() -> new NotFoundException("Aucun utilisateur existant avec cet uid"));
        return utilisateurEntityMapper.mapToDomain(monUtilisateurEntity);
    }

    @Override
    public void enregistrerUtilisateur(Utilisateur utilisateur) {
        UtilisateurEntity utilisateurEntityAEnregistrer = utilisateurEntityMapper.mapToEntity(utilisateur);
        userJpaRepository.save(utilisateurEntityAEnregistrer);
    }

    @Override
    public void modifierUtilisateur(Utilisateur utilisateurAModifier) {
        userJpaRepository.save(utilisateurEntityMapper.mapToEntity(utilisateurAModifier));
    }

    @Override
    public void supprimerUser(Utilisateur utilisateurASupprimer) {
        userJpaRepository.delete(utilisateurEntityMapper.mapToEntity(utilisateurASupprimer));
    }

    @Override
    public void creerUser(Utilisateur utilisateur) {
        userJpaRepository.save(utilisateurEntityMapper.mapToEntity(utilisateur));
    }

    @Override
    public Utilisateur rechercherUser(String login) {
        UtilisateurEntity monUtilisateurEntity = userJpaRepository.findByLogin(login).orElse(null);
        if (monUtilisateurEntity != null){
            return utilisateurEntityMapper.mapToDomain(monUtilisateurEntity);
        } else{
            return null;
        }
    }

}
