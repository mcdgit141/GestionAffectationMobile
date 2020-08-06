package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.iphone.IRepositoryModeleIphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryModeleIphoneImpl implements IRepositoryModeleIphone {

    @Autowired
    private IRepositoryJpaModeleIphone repositoryJpaModeleIphone;

    @Autowired
    private ModeleIphoneEntityMapper modeleIphoneEntityMapper;

    @Override
    public ModeleIphone rechercheNomModele(final String nomModele) {
        final ModeleIphoneEntity modeleIphoneEntity = repositoryJpaModeleIphone.findByNomModele(nomModele);
        if (modeleIphoneEntity != null) {
            return modeleIphoneEntityMapper.mapToDomain(modeleIphoneEntity);
        } else {
            throw new NotFoundException("Ce nom de modele d'Iphone n'existe pas : " + nomModele);
        }
    }
}
