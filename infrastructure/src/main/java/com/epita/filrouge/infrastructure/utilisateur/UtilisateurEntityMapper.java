package com.epita.filrouge.infrastructure.utilisateur;



import com.epita.filrouge.domain.utilisateur.Utilisateur;
import com.epita.filrouge.infrastructure.AbstractMapper;

import org.springframework.stereotype.Component;

@Component
public class UtilisateurEntityMapper extends  AbstractMapper<Utilisateur, UtilisateurEntity> {

    @Override
    public Utilisateur mapToDomain(UtilisateurEntity utilisateurEntity) {

        return new Utilisateur(utilisateurEntity.getId(), utilisateurEntity.getUid(),utilisateurEntity.getNom(), utilisateurEntity.getPrenom(),
                  utilisateurEntity.getLogin(), utilisateurEntity.getPassword(), utilisateurEntity.getUserRole());

    }
    @Override
    public UtilisateurEntity mapToEntity(Utilisateur utilisateur) {
        return new UtilisateurEntity(utilisateur.getId(), utilisateur.getUid(), utilisateur.getNom(), utilisateur.getPrenom(),
                utilisateur.getLogin(), utilisateur.getPassword() ,utilisateur.getUserRole());
    }
}
