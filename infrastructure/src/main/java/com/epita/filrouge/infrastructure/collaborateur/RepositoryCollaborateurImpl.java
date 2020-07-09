package com.epita.filrouge.infrastructure.collaborateur;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.collaborateur.IRepositoryCalloborateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryCollaborateurImpl implements IRepositoryCalloborateur {

    @Autowired
    private IRepositoryJpaCollaborateur repositoryJpaCollaborateur;


    @Override
    public Collaborateur findByUid(String uid) {


        CollaborateurEntity collaborateurEntity =  repositoryJpaCollaborateur.getOne(uid);

        return new Collaborateur(collaborateurEntity.getUid(), collaborateurEntity.getPrenom(), collaborateurEntity.getNom());
//        return null ;

    }
}
