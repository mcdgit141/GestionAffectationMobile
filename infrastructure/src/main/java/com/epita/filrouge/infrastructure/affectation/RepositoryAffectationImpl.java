package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.collaborateur.IRepositoryJpaCollaborateur;
import com.epita.filrouge.infrastructure.iphone.IRepositoryJpaIphone;
import com.epita.filrouge.infrastructure.iphone.IphoneEntity;
import com.epita.filrouge.infrastructure.iphone.ModeleIphoneEntity;
import com.epita.filrouge.infrastructure.uo.UoEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositoryAffectationImpl implements IRepositoryAffectation {

    @Autowired
    private AffectationEntityMapper affectationMapper;

    @Autowired
    IRepositoryJpaAffectation iRepositoryJpaAffectation;

    @Autowired
    IRepositoryJpaCollaborateur iRepositoryJpaCollaborateur;

    @Autowired
    IRepositoryJpaIphone iRepositoryJpaIphone;

    Logger monLogger = LoggerFactory.getLogger(RepositoryAffectationImpl.class);

    @Autowired
    private EntityManager monEntityManager;

    @Autowired
    UoEntityMapper uoEntityMapper;

    @Autowired
    AffectationEntityMapper affectationEntityMapper;

    @Override
    public void affecter(Affectation affectationACreer) {

        System.out.println("affectationACreer = " + affectationACreer);
//        Collaborateur collaborateur = affectationACreer.getCollaborateur();
//        CollaborateurEntity collaborateurEntity = new CollaborateurEntity();
////        collaborateurEntity.setCollaborateurId(collaborateur.getId());
//        collaborateurEntity.setUid(collaborateur.getUid());
//        collaborateurEntity.setNom(collaborateur.getNom());
//        collaborateurEntity.setPrenom(collaborateur.getPrenom());
//        collaborateurEntity.setNumeroLigne(collaborateur.getNumeroLigne());
//
//        collaborateurEntity.setUo(uoEntityMapper.mapToEntity(collaborateur.getUo()));
//        collaborateurEntity.setAffectationCollaborateur(affectationEntityMapper.mapToEntityList(collaborateur.getAffectationCollaborateur()));

        CollaborateurEntity monCollaborateurEntity = iRepositoryJpaCollaborateur.findByUid(affectationACreer.getCollaborateur().getUid());

//        System.out.println("collaborateurEntity.getCollaborateurId() = " + collaborateurEntity.getCollaborateurId());
//        System.out.println("collaborateurEntity.getUid() = " + collaborateurEntity.getUid());
//        System.out.println("collaborateurEntity.getNom() = " + collaborateurEntity.getNom());
//        System.out.println("collaborateurEntity.getPrenom() = " + collaborateurEntity.getPrenom());
//        System.out.println("collaborateurEntity.getCollaborateurId() = " + collaborateurEntity.getCollaborateurId());

//        Iphone iPhone = affectationACreer.getIphone();
//        ModeleIphone modeleIphone = iPhone.getModeleIphone();

//        ModeleIphoneEntity modeleIphoneEntity = new ModeleIphoneEntity();
//        modeleIphoneEntity.setNomModele(modeleIphone.getNomModele());
//        modeleIphoneEntity.setModeleId(modeleIphone.getModeleID());
//        System.out.println("infra modeleIphoneEntity.getModeleId() = " + modeleIphoneEntity.getModeleId());
//        System.out.println("infra modeleIphoneEntity.getNomModele() = " + modeleIphoneEntity.getNomModele());


//        IphoneEntity iphoneEntity = new IphoneEntity();
//        iphoneEntity.setIphoneId(iPhone.getIphoneId());
//        iphoneEntity.setNumeroSerie(iPhone.getNumeroSerie());
//        iphoneEntity.setEtatIphone(iPhone.getEtatIphone());
//        iphoneEntity.setModeleIphoneEntity(modeleIphoneEntity);
//        System.out.println("iphoneEntity.getIphoneId() = " + iphoneEntity.getIphoneId());
//        System.out.println("iphoneEntity.getEtatIphone() = " + iphoneEntity.getEtatIphone());
//        System.out.println("iphoneEntity.getNumeroSerie() = " + iphoneEntity.getNumeroSerie());

        IphoneEntity monIphoneEntity = iRepositoryJpaIphone.findByNumeroSerie(affectationACreer.getIphone().getNumeroSerie());

        AffectationEntity affectationEntity = new AffectationEntity();

        affectationEntity.setNumeroAffectation(affectationACreer.getNumeroAffectation());
        affectationEntity.setDateAffectation(affectationACreer.getDateAffectation());
        affectationEntity.setDateRenouvellementPrevue(affectationACreer.getDateRenouvellementPrevue());
        affectationEntity.setDateFin(affectationACreer.getDateFin());
        affectationEntity.setCommentaire(affectationACreer.getCommentaire());
        affectationEntity.setMotifFin(affectationACreer.getMotifFin());
//        affectationEntity.setCollaborateur(collaborateurEntity);
        affectationEntity.setCollaborateur(monCollaborateurEntity);
        affectationEntity.setIphone(monIphoneEntity);

        iRepositoryJpaAffectation.save(affectationEntity);
    }

    @Override
    public List<Affectation> listerAffectation() {
        return affectationMapper.mapToDomainList(iRepositoryJpaAffectation.findAll());
    }

    @Override
    public List<Affectation> rechercheAffectationByUid(String collaborateurUid) {

        List<AffectationEntity> affectationsList = iRepositoryJpaAffectation.findByCollaborateurUid(collaborateurUid);

        return affectationMapper.mapToDomainList(affectationsList);
    }

    @Override
    public List<Affectation> rechercheAffectationAvecFiltres(String uid, String nom, String codeUo, String nomUsageUo, String nomSite, String numeroLigneCollaborateur, String nomModeleIphone, LocalDate dateRenouvMin, LocalDate dateRenouvMax) {
        StringBuilder query = new StringBuilder();
        query.append("select a from AffectationEntity a where 1=1 ");
//        String query = "select a from AffectationEntity a where 1=1 ";
        if (uid != null & uid !=""){
            query.append(String.format("AND a.collaborateur.uid = '%s' ", uid ));

        }
        if (nom != null & nom !=""){
            query.append(String.format("AND a.collaborateur.nom = '%s' ", nom ));
        }
        if (codeUo != null & codeUo !=""){
            query.append(String.format("AND a.collaborateur.uo.codeUo = '%s' ",  codeUo));

        }
        if (nomUsageUo != null & nomUsageUo !=""){
            query.append(String.format("AND a.collaborateur.uo.nomUsageUo = '%s' ", nomUsageUo));

        }
        if (nomSite != null & nomSite !=""){
            query.append(String.format("AND a.collaborateur.uo.siteExercice.nomSite = '%s' ", nomSite ));

        }
        if (numeroLigneCollaborateur != null & numeroLigneCollaborateur !=""){
            query.append(String.format("AND a.collaborateur.numeroLigne = '%s' ", numeroLigneCollaborateur));

        }
        if (nomModeleIphone != null & nomModeleIphone !=""){
            query.append(String.format("AND a.iphone.modeleIphoneEntity.nomModele = '%s' ", nomModeleIphone));
        }
        if (dateRenouvMin != null){
            query.append("AND a.dateRenouvellementPrevue > '" + dateRenouvMin + "' ");
        }
        if (dateRenouvMax != null){
            query.append("AND a.dateRenouvellementPrevue < '" + dateRenouvMax + "' ");
        }


        String maRequeteConstruite = query.toString();
        monLogger.debug(maRequeteConstruite);
        System.out.println(maRequeteConstruite);

        List<AffectationEntity> maListEntity = monEntityManager.createQuery(maRequeteConstruite).getResultList();
        List<Affectation> maList = new ArrayList<>();
        for (AffectationEntity affectationEntity : maListEntity) {
            maList.add(affectationMapper.mapToDomain(affectationEntity));
        }

        return maList;
    }


}
