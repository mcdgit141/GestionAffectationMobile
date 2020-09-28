package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.infrastructure.AbstractMapper;
import com.epita.filrouge.infrastructure.affectation.AffectationEntityMapper;
import com.epita.filrouge.infrastructure.affectation.IRepositoryJpaAffectation;
import com.epita.filrouge.infrastructure.uo.UoEntityMapper;
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

    @Autowired
    private IRepositoryJpaAffectation repositoryJpaAffectation;

    @Autowired
    private UoEntityMapper uoEntityMapper;

    @Override
    public Iphone mapToDomain(IphoneEntity iphoneEntity)
    {
        final Iphone iphone = new Iphone(iphoneEntity.getIphoneId(),iphoneEntity.getNumeroSerie(),iphoneEntity.getPrixIphone(),
                modeleIphoneMapper.mapToDomain(iphoneEntity.getModeleIphoneEntity()),iphoneEntity.getEtatIphone());

        return iphone;
    }

    @Override
    public IphoneEntity mapToEntity(Iphone iphone) {
        final IphoneEntity iphoneEntity = new IphoneEntity();
        iphoneEntity.setNumeroSerie(iphone.getNumeroSerie());
        iphoneEntity.setPrixIphone(iphone.getPrixIphone());
        iphoneEntity.setEtatIphone(iphone.getEtatIphone());

        String nomModeleMapper = iphone.getModeleIphone().getNomModele();
        ModeleIphoneEntity modeleIphoneEntity = repositoryJpaModeleIphone.findByNomModele(nomModeleMapper);
        iphoneEntity.setModeleIphoneEntity(modeleIphoneEntity);


        return iphoneEntity;
    }

}
