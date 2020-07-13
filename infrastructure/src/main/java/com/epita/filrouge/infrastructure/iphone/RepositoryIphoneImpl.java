package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.domain.iphone.IRepositoryIphone;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryIphoneImpl implements IRepositoryIphone {

    @Autowired
    private IRepositoryJpaIphone repositoryJpaIphone;

    @Override
    public Iphone findByNomModele(String nomModele) {

        IphoneEntity iphoneEntity =  repositoryJpaIphone.findByNomModele(nomModele);

        return new Iphone(iphoneEntity.getNumeroSerie(),iphoneEntity.getPrixIphone(),iphoneEntity.getModeleIphone(),iphoneEntity.getEtatIphone());
       }
}
