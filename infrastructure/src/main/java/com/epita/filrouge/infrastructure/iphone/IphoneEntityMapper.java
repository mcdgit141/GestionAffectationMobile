package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.infrastructure.AbstractMapper;
import com.epita.filrouge.infrastructure.affectation.AffectationEntity;
import com.epita.filrouge.infrastructure.affectation.AffectationEntityMapper;
import com.epita.filrouge.infrastructure.affectation.IRepositoryJpaAffectation;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.uo.UoEntity;
import com.epita.filrouge.infrastructure.uo.UoEntityMapper;
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

    @Autowired
    private UoEntityMapper uoEntityMapper;

    @Override
    public Iphone mapToDomain(IphoneEntity iphoneEntity)
    {
        final Iphone iphone = new Iphone(iphoneEntity.getIphoneId(),iphoneEntity.getNumeroSerie(),iphoneEntity.getPrixIphone(),
                modeleIphoneMapper.mapToDomain(iphoneEntity.getModeleIphoneEntity()),iphoneEntity.getEtatIphone());
//        if (iphoneEntity.getAffectationIphone() != null) {
//            for (final AffectationEntity affectationEntity : iphoneEntity.getAffectationIphone()) {
//
//                UoEntity uoEntityFourni = affectationEntity.getCollaborateur().getUo();
//
//                CollaborateurEntity collaborateurEntityFourni = affectationEntity.getCollaborateur();
//                Collaborateur monCollaborateur = new Collaborateur(collaborateurEntityFourni.getUid(), collaborateurEntityFourni.getNom(),
//                        collaborateurEntityFourni.getPrenom(), collaborateurEntityFourni.getNumeroLigne(), uoEntityMapper.mapToDomain(uoEntityFourni));
//
//                IphoneEntity iphoneEntityFourni = affectationEntity.getIphone();
//                Iphone monIphone = new Iphone(iphoneEntity.getIphoneId(), iphoneEntity.getNumeroSerie(), iphoneEntity.getPrixIphone(),
//                        modeleIphoneMapper.mapToDomain(iphoneEntity.getModeleIphoneEntity()),iphoneEntity.getEtatIphone());
//
//                Affectation monAffectation = new Affectation(affectationEntity.getNumeroAffectation(),affectationEntity.getDateAffectation(),
//                        affectationEntity.getCommentaire(), monCollaborateur, monIphone);
//                iphone.addAffectation(monAffectation);
//            }
//        }
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

//        List<AffectationEntity> listAffectationEntity = repositoryJpaAffectation.findByIphoneNumeroSerie(iphone.getNumeroSerie());
//        iphoneEntity.setAffectationIphone(listAffectationEntity);

        return iphoneEntity;
    }

}
