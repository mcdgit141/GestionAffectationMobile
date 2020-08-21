package com.epita.filrouge.application.collaborateur;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.collaborateur.IRepositoryCollaborateur;
import com.epita.filrouge.domain.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollaborateurManagementImpl implements ICollaborateurManagement {

    @Autowired
    private IRepositoryCollaborateur repositoryCollaborateur;

    @Override
    public Collaborateur findByUid(String uid) throws NotFoundException{
        Collaborateur collaborateur = repositoryCollaborateur.findByUid(uid);
        if (collaborateur != null) {
            return collaborateur ;
        } else {
            throw new NotFoundException("Le collaborateur par recherche sur l'UID suivant est non trouvé = " + uid);
        }
    }

    @Override
    public Collaborateur findByNumeroLigne(String numeroLigne) throws NotFoundException{

        Collaborateur collaborateur = repositoryCollaborateur.findByNumeroLigne(numeroLigne);
        if (collaborateur != null) {
            return collaborateur ;
        } else {
            throw new NotFoundException("Le collaborateur par recherche du numéro de ligne suivant est non trouvé = " + numeroLigne);
        }
    }
}
