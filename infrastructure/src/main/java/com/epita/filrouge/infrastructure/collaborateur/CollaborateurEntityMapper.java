package com.epita.filrouge.infrastructure.collaborateur;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.infrastructure.AbstractMapper;
import com.epita.filrouge.infrastructure.affectation.AffectationEntityMapper;
import com.epita.filrouge.infrastructure.affectation.IRepositoryJpaAffectation;
import com.epita.filrouge.infrastructure.iphone.ModeleIphoneEntityMapper;
import com.epita.filrouge.infrastructure.uo.IRepositoryJpaUo;
import com.epita.filrouge.infrastructure.uo.UoEntity;
import com.epita.filrouge.infrastructure.uo.UoEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CollaborateurEntityMapper extends AbstractMapper<Collaborateur,CollaborateurEntity> {
    @Autowired
    private AffectationEntityMapper affectationMapper;

    @Autowired
    private UoEntityMapper uoMapper;

    @Autowired
    private IRepositoryJpaUo repositoryJpaUo;

    @Autowired
    private IRepositoryJpaAffectation repositoryJpaAffectation;

    @Autowired
    private ModeleIphoneEntityMapper modeleIphoneEntityMapper;

    @Override
    public Collaborateur mapToDomain(final CollaborateurEntity collaborateurEntity) {


        final Collaborateur collaborateur = new Collaborateur(collaborateurEntity.getUid(),collaborateurEntity.getNom(),
                collaborateurEntity.getPrenom(),collaborateurEntity.getNumeroLigne(),
                uoMapper.mapToDomain(collaborateurEntity.getUo()));

        return collaborateur;
    }

    @Override
    public CollaborateurEntity mapToEntity(final Collaborateur collaborateur) {

        final CollaborateurEntity collaborateurEntity = new CollaborateurEntity();
        collaborateurEntity.setUid(collaborateur.getUid());
        collaborateurEntity.setNom(collaborateur.getNom());
        collaborateurEntity.setPrenom(collaborateur.getPrenom());
        collaborateurEntity.setNumeroLigne(collaborateur.getNumeroLigne());

        String codeUoMapper = collaborateur.getUo().getCodeUo();
        UoEntity uoEntity = repositoryJpaUo.findByCodeUo(codeUoMapper);

        collaborateurEntity.setUo(uoEntity);

        return collaborateurEntity;
    }
    }
