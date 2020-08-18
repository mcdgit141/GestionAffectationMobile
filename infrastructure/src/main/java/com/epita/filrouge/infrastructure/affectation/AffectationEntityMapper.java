package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.infrastructure.AbstractMapper;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntityMapper;
import com.epita.filrouge.infrastructure.collaborateur.IRepositoryJpaCollaborateur;
import com.epita.filrouge.infrastructure.iphone.IRepositoryJpaIphone;
import com.epita.filrouge.infrastructure.iphone.IphoneEntity;
import com.epita.filrouge.infrastructure.iphone.IphoneEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AffectationEntityMapper extends AbstractMapper<Affectation,AffectationEntity> {

    @Autowired
    private CollaborateurEntityMapper collaborateurMapper;

    @Autowired
    private IphoneEntityMapper iphoneMapper;

    @Autowired
    private IRepositoryJpaCollaborateur repositoryJpaCollaborateur;

    @Autowired
    private IRepositoryJpaIphone repositoryJpaIphone;

    @Override
    public Affectation mapToDomain(final AffectationEntity affectationEntity) {

        final Affectation affectation = new Affectation(affectationEntity.getNumeroAffectation(),affectationEntity.getDateAffectation(),
                                                        affectationEntity.getCommentaire(),
                                                        collaborateurMapper.mapToDomain(affectationEntity.getCollaborateur()),
                                                        iphoneMapper.mapToDomain(affectationEntity.getIphone()));

        affectation.setDateFin(affectationEntity.getDateFin());
        affectation.setDateRenouvellementPrevue(affectationEntity.getDateRenouvellementPrevue());
        affectation.setMotifFin(affectationEntity.getMotifFin());
        return affectation;
    }

    @Override
    public AffectationEntity mapToEntity(final Affectation affectation){

        final AffectationEntity affectationEntity = new AffectationEntity();

        CollaborateurEntity collaborateurEntity = repositoryJpaCollaborateur.findByUid(affectation.getCollaborateur().getUid());
        collaborateurEntity.setNumeroLigne(affectation.getCollaborateur().getNumeroLigne());
        collaborateurEntity.setNom(affectation.getCollaborateur().getNom());
        collaborateurEntity.setPrenom(affectation.getCollaborateur().getPrenom());
        collaborateurEntity.setUid(affectation.getCollaborateur().getUid());

        IphoneEntity iphoneEntity = repositoryJpaIphone.findByNumeroSerie(affectation.getIphone().getNumeroSerie());
        iphoneEntity.setEtatIphone(affectation.getIphone().getEtatIphone());
        iphoneEntity.setNumeroSerie(affectation.getIphone().getNumeroSerie());
        iphoneEntity.setPrixIphone(affectation.getIphone().getPrixIphone());

        affectationEntity.setIphone(iphoneEntity);
        affectationEntity.setCollaborateur(collaborateurEntity);
        affectationEntity.setNumeroAffectation(affectation.getNumeroAffectation());
        affectationEntity.setCommentaire(affectation.getCommentaire());
        affectationEntity.setDateAffectation(affectation.getDateAffectation());
        affectationEntity.setDateFin(affectation.getDateFin());
        affectationEntity.setMotifFin(affectation.getMotifFin());
        affectationEntity.setDateRenouvellementPrevue(affectation.getDateRenouvellementPrevue());

        return affectationEntity;
    }
}
