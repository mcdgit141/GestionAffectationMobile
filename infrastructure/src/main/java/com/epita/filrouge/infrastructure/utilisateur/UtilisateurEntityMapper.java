package com.epita.filrouge.infrastructure.utilisateur;



import com.epita.filrouge.domain.utilisateur.IRepositoryUtilisateur;
import com.epita.filrouge.domain.utilisateur.Utilisateur;
import com.epita.filrouge.infrastructure.AbstractMapper;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.collaborateur.IRepositoryJpaCollaborateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurEntityMapper extends  AbstractMapper<Utilisateur, UtilisateurEntity> {

    @Override
    public Utilisateur mapToDomain(UtilisateurEntity utilisateurEntity) {

        return new Utilisateur(utilisateurEntity.getId(), utilisateurEntity.getCollaborateurLight().getUid()
                ,utilisateurEntity.getCollaborateurLight().getNom(), utilisateurEntity.getCollaborateurLight().getPrenom(),
                utilisateurEntity.getLogin(), utilisateurEntity.getPassword(), utilisateurEntity.getUserRole());
    }
    @Override
    public UtilisateurEntity mapToEntity(Utilisateur utilisateur) {
        CollaborateurLightEntity collaborateurLightEntity = new CollaborateurLightEntity(utilisateur.getUid(),utilisateur.getNom(),
                utilisateur.getPrenom());
        return new UtilisateurEntity(utilisateur.getId(),collaborateurLightEntity, utilisateur.getLogin(), utilisateur.getPassword()
                ,utilisateur.getUserRole());
    }
}
