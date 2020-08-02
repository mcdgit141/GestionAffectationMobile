package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.infrastructure.AbstractMapper;
import com.epita.filrouge.infrastructure.affectation.AffectationEntity;
import com.epita.filrouge.infrastructure.affectation.AffectationEntityMapper;
import com.epita.filrouge.infrastructure.site.IRepositoryJpaSiteExercice;
import com.epita.filrouge.infrastructure.site.SiteExerciceEntity;
import com.epita.filrouge.infrastructure.site.SiteExerciceEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IphoneEntityMapper extends AbstractMapper<Iphone, IphoneEntity> {

    @Autowired
    private ModeleIphoneEntityMapper modeleIphoneMapper;

    @Autowired
    private IRepositoryJpaModeleIphone repositoryJpaModeleIphone;

    @Autowired
    private AffectationEntityMapper affectationMapper;

    @Override
    public Iphone mapToDomain(IphoneEntity iphoneEntity)
    {
        final Iphone iphone = new Iphone(iphoneEntity.getIphoneId(),iphoneEntity.getNumeroSerie(),iphoneEntity.getPrixIphone(),
                modeleIphoneMapper.mapToDomain(iphoneEntity.getModeleIphoneEntity()),iphoneEntity.getEtatIphone());
        for (final AffectationEntity affectationEntity : iphoneEntity.getAffectationIphone()) {
            iphone.addAffectation(affectationMapper.mapToDomain(affectationEntity));
        }

        return iphone;
    }

    @Override
    public IphoneEntity mapToEntity(Iphone iphone) {
        final IphoneEntity iphoneEntity = new IphoneEntity();
        iphoneEntity.setNumeroSerie(iphone.getNumeroSerie());
        iphoneEntity.setPrixIphone(iphone.getPrixIphone());
        iphoneEntity.setEtatIphone(iphone.getEtatIphone());
        iphoneEntity.setModeleIphoneEntity(getModeleIphoneEntity(iphone.getModeleIphone()));
        iphoneEntity.setAffectationIphone(affectationMapper.mapToEntityList(iphone.getAffectationIphone()));
        return iphoneEntity;
    }
    private ModeleIphoneEntity getModeleIphoneEntity(ModeleIphone modeleIphone) {
        return repositoryJpaModeleIphone.findByNomModele(modeleIphone.getNomModele());
    }

}
