package com.epita.filrouge.infrastructure.collaborateur;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.infrastructure.AbstractMapper;
import com.epita.filrouge.infrastructure.affectation.AffectationEntity;
import com.epita.filrouge.infrastructure.affectation.AffectationEntityMapper;
import com.epita.filrouge.infrastructure.affectation.IRepositoryJpaAffectation;
import com.epita.filrouge.infrastructure.iphone.IphoneEntity;
import com.epita.filrouge.infrastructure.iphone.ModeleIphoneEntity;
import com.epita.filrouge.infrastructure.uo.IRepositoryJpaUo;
import com.epita.filrouge.infrastructure.uo.UoEntity;
import com.epita.filrouge.infrastructure.uo.UoEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public Collaborateur mapToDomain(final CollaborateurEntity collaborateurEntity) {
        UoEntity uoEntityRecu = collaborateurEntity.getUo();

        Uo monUo = new Uo(uoEntityRecu.getCodeUo(), uoEntityRecu.getFonctionRattachement(),uoEntityRecu.getCodeUoParent(),
                uoEntityRecu.getNomUsageUo(), uoEntityRecu.getNomResponsableUo());

        final Collaborateur collaborateur = new Collaborateur(collaborateurEntity.getUid(),collaborateurEntity.getNom(),
                                            collaborateurEntity.getPrenom(),collaborateurEntity.getNumeroLigne(), monUo);

        for (final AffectationEntity affectationEntity : collaborateurEntity.getAffectationCollaborateur()) {
            UoEntity monUoEntity = affectationEntity.getCollaborateur().getUo();
            CollaborateurEntity monCollaborateurEntity = affectationEntity.getCollaborateur();
            ModeleIphoneEntity monModelIphoneEntity = affectationEntity.getIphone().getModeleIphoneEntity();
            IphoneEntity monIphoneEntity = affectationEntity.getIphone();
            Uo uoConstruit = new Uo(monUoEntity.getCodeUo(), monUoEntity.getFonctionRattachement(), monUoEntity.getCodeUoParent(), monUoEntity.getNomUsageUo(),
                    monUoEntity.getNomResponsableUo());
            Collaborateur collaborateurConstruit = new Collaborateur(monCollaborateurEntity.getUid(), monCollaborateurEntity.getNom(), monCollaborateurEntity.getPrenom(),
                    monCollaborateurEntity.getNumeroLigne(), uoConstruit);

            ModeleIphone modeleIphoneConstruit = new ModeleIphone(monIphoneEntity.getIphoneId(), monModelIphoneEntity.getNomModele());
            Iphone iphoneConstruit = new Iphone(monIphoneEntity.getIphoneId(), monIphoneEntity.getNumeroSerie(),monIphoneEntity.getPrixIphone(), modeleIphoneConstruit,
                    monIphoneEntity.getEtatIphone());


            Affectation affectationContruite = new Affectation(affectationEntity.getNumeroAffectation(), affectationEntity.getDateAffectation(),
                    affectationEntity.getCommentaire(), collaborateurConstruit, iphoneConstruit);
            collaborateur.addAffectationCollaborateur(affectationContruite);
        }

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
        List<AffectationEntity> listAffectationEntityEnBase = repositoryJpaAffectation.findByCollaborateurUid(collaborateur.getUid());
        collaborateurEntity.setAffectationCollaborateur(listAffectationEntityEnBase);

        return collaborateurEntity;
    }

}
