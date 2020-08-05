package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.infrastructure.AbstractMapper;
import com.epita.filrouge.infrastructure.affectation.AffectationEntity;
import com.epita.filrouge.infrastructure.affectation.AffectationEntityMapper;
import com.epita.filrouge.infrastructure.affectation.IRepositoryJpaAffectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public Iphone mapToDomain(IphoneEntity iphoneEntity)
    {
        final Iphone iphone = new Iphone(iphoneEntity.getIphoneId(),iphoneEntity.getNumeroSerie(),iphoneEntity.getPrixIphone(),
                modeleIphoneMapper.mapToDomain(iphoneEntity.getModeleIphoneEntity()),iphoneEntity.getEtatIphone());
        if (iphoneEntity.getAffectationIphone() != null) {
            for (final AffectationEntity affectationEntity : iphoneEntity.getAffectationIphone()) {
                iphone.addAffectation(affectationMapper.mapToDomain(affectationEntity));
            }
        }
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


        List<AffectationEntity> listAffectationEnBase = repositoryJpaAffectation.findbyIphoneNumeroSerie(iphone.getNumeroSerie());
        iphoneEntity.setAffectationIphone(affectationMapper.mapToEntityList(iphone.getAffectationIphone()));
        return iphoneEntity;
    }

}
