package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.IRepositoryIphone;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositoryIphoneImpl implements IRepositoryIphone {

    @Autowired
    private IRepositoryJpaIphone repositoryJpaIphone;

    @Override
    public Iphone rechercheIphoneParNomModele(String nomModele) {
        // modification suite à ajout de la list pour ne prendre que le premier enregistrement envoyé car pas de règles métier. On prend
        // le premier iphone disponible

        List<IphoneEntity> iphoneEntityList = repositoryJpaIphone.findByModeleIphoneEntityNomModele(nomModele);
        if (iphoneEntityList != null) {
            for (IphoneEntity iphoneElement : iphoneEntityList) {
                if (iphoneElement.getEtatIphone() == EtatIphoneEnum.DISPONIBLE) {
                    ModeleIphoneEntity modeleIphoneEntity = iphoneElement.getModeleIphoneEntity();
                    ModeleIphone modeleIphone = new ModeleIphone(modeleIphoneEntity.getModeleId(), modeleIphoneEntity.getNomModele());

                    return new Iphone(iphoneElement.getIphoneId(), iphoneElement.getNumeroSerie(), iphoneElement.getPrixIphone(), modeleIphone, iphoneElement.getEtatIphone());
                }
            }
        } else {
            throw new NotFoundException("L'iphone par la recherche du nom de modèle est non trouvé  = " + nomModele);
        }
        return null;
    }

    @Override
    public Iphone rechercheIphoneParNumeroSerie(String iPhoneNumeroSerie) {
        IphoneEntity iphoneEntity = repositoryJpaIphone.findByNumeroSerie(iPhoneNumeroSerie);
        if (iphoneEntity != null) {
            ModeleIphoneEntity modeleIphoneEntity = iphoneEntity.getModeleIphoneEntity();
            ModeleIphone modeleIphone = new ModeleIphone(modeleIphoneEntity.getModeleId(), modeleIphoneEntity.getNomModele());

            return new Iphone(iphoneEntity.getIphoneId(), iphoneEntity.getNumeroSerie(), iphoneEntity.getPrixIphone(), modeleIphone, iphoneEntity.getEtatIphone());
        } else{
            throw new NotFoundException("L'iphone par la recherche du numéro de série est non trouvé  = " + iPhoneNumeroSerie);
        }
    }

}
