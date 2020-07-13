package com.epita.filrouge.application.iphone;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.collaborateur.IRepositoryCollaborateur;
import com.epita.filrouge.domain.iphone.IRepositoryIphone;
import com.epita.filrouge.domain.iphone.Iphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IphoneManagementImpl {

    @Autowired
    private IRepositoryIphone repositoryIphone;

    @Override
    public Iphone findByNomModele(String nomModele) {

        return repositoryIphone.findByNomModele(nomModele);
    }

}
