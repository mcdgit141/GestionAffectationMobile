package com.epita.filrouge.infrastructure.utilisateur;



import com.epita.filrouge.domain.utilisateur.Utilisateur;
import com.epita.filrouge.infrastructure.AbstractMapper;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.collaborateur.IRepositoryJpaCollaborateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper extends  AbstractMapper<Utilisateur, UtilisateurEntity> {

    @Autowired
    private IRepositoryJpaCollaborateur repositoryJpaCollaborateur;

    @Override
    public Utilisateur mapToDomain(UtilisateurEntity utilisateurEntity) {
        CollaborateurEntity collaborateurCorrespondant = repositoryJpaCollaborateur.findByUid(utilisateurEntity.getUid());

        Utilisateur monUtilisateur = new Utilisateur(utilisateurEntity.getUid(), collaborateurCorrespondant.getNom(), collaborateurCorrespondant.getPrenom(),utilisateurEntity.getUserRole());
        monUtilisateur.setPassword(utilisateurEntity.getPassword());
        monUtilisateur.setLogin(utilisateurEntity.getLogin());

        return monUtilisateur;
    }
    @Override
    public UtilisateurEntity mapToEntity(Utilisateur utilisateur) {

        UtilisateurEntity monUtilisateurEntity = new UtilisateurEntity();
        monUtilisateurEntity.setUid(utilisateur.getUid());
        monUtilisateurEntity.setLogin(utilisateur.getLogin());
        monUtilisateurEntity.setPassword(utilisateur.getPassword());
        monUtilisateurEntity.setUserRole(utilisateur.getUserRole());

        return monUtilisateurEntity;
    }
}
