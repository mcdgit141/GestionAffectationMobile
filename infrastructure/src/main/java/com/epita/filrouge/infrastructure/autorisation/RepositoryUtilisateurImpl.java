package com.epita.filrouge.infrastructure.autorisation;

import com.epita.filrouge.domain.autorisation.IRepositoryUtilisateur;
import com.epita.filrouge.domain.autorisation.Utilisateur;
import com.epita.filrouge.domain.autorisation.UtilisateurRoleEnum;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.collaborateur.IRepositoryJpaCollaborateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositoryUtilisateurImpl implements IRepositoryUtilisateur {

    @Autowired
    private IRepositoryJpaUtilisateur userJpaRepository;

    @Autowired
    private IRepositoryJpaCollaborateur collaborateurJpaRepository;

    @Override
    public Utilisateur creerUser(Utilisateur utilisateur) {

        CollaborateurEntity collabo = collaborateurJpaRepository.findByUid(utilisateur.getUid());
        Utilisateur utilisateurEnrichi = new Utilisateur(utilisateur.getUid(), utilisateur.getLogin());
        utilisateurEnrichi.setNom(collabo.getNom());
        utilisateurEnrichi.setPrenom(collabo.getPrenom());
        UtilisateurEntity monUtilisateurEntity = UtilisateurMapper.mapToInfra(utilisateurEnrichi);

        userJpaRepository.save(monUtilisateurEntity);

        return UtilisateurMapper.mapToDomain(monUtilisateurEntity);
    }

    @Override
    public Utilisateur rechercherUser(String login) {
        UtilisateurEntity monUtilisateurEntity = userJpaRepository.findByLogin(login);
        return UtilisateurMapper.mapToDomain(monUtilisateurEntity);
    }

    @Override
    public List<Utilisateur> findAllUser() {
        return null;
    }

    @Override
    public List<Utilisateur> rechercherParUserRole(UtilisateurRoleEnum userRole) {
        return null;
    }

    @Override
    public void upgradeUser(Utilisateur utilisateur) {
        UtilisateurEntity monUtilisateurEntity = userJpaRepository.findByLogin(utilisateur.getLogin());
        monUtilisateurEntity.setUserRole(UtilisateurRoleEnum.ROLE_TYPE2);
    }

    @Override
    public void setAdmin(Utilisateur utilisateur) {
        UtilisateurEntity monUtilisateurEntity = userJpaRepository.findByLogin(utilisateur.getLogin());
        monUtilisateurEntity.setUserRole(UtilisateurRoleEnum.ROLE_ADMIN);
    }
}
