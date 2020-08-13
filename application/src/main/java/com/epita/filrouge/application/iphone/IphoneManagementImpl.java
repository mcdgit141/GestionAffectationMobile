package com.epita.filrouge.application.iphone;

import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.iphone.IRepositoryIphone;
import com.epita.filrouge.domain.iphone.Iphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IphoneManagementImpl implements IIphoneManagement{

    @Autowired
    private IRepositoryIphone repositoryIphone;

    @Override
    public Iphone rechercheIphoneParNomModele(String nomModele) throws NotFoundException {

        return repositoryIphone.rechercheIphoneParNomModele(nomModele);
    }

}
