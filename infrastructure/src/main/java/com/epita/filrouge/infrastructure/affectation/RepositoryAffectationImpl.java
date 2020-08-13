package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.FiltresAffectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.collaborateur.IRepositoryJpaCollaborateur;
import com.epita.filrouge.infrastructure.iphone.IRepositoryJpaIphone;
import com.epita.filrouge.infrastructure.iphone.IphoneEntity;
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

        monLogger.debug("affectationACreer = {}" , affectationACreer);


        CollaborateurEntity monCollaborateurEntity = iRepositoryJpaCollaborateur.findByUid(affectationACreer.getCollaborateur().getUid());

        IphoneEntity monIphoneEntity = iRepositoryJpaIphone.findByNumeroSerie(affectationACreer.getIphone().getNumeroSerie());

        AffectationEntity affectationEntity = new AffectationEntity();

        affectationEntity = affectationEntityMapper.mapToEntity(affectationACreer);

//        affectationEntity.setNumeroAffectation(affectationACreer.getNumeroAffectation());
//        affectationEntity.setDateAffectation(affectationACreer.getDateAffectation());
//        affectationEntity.setDateRenouvellementPrevue(affectationACreer.getDateRenouvellementPrevue());
//        affectationEntity.setDateFin(affectationACreer.getDateFin());
//        affectationEntity.setCommentaire(affectationACreer.getCommentaire());
//        affectationEntity.setMotifFin(affectationACreer.getMotifFin());
//        affectationEntity.setCollaborateur(monCollaborateurEntity);
//        affectationEntity.setIphone(monIphoneEntity);

        iRepositoryJpaAffectation.save(affectationEntity);
    }
    @Override
    public Affectation chercheAffectationParNumeroAffectation(Long numeroAffectation) {

        AffectationEntity affectationEntity = iRepositoryJpaAffectation.findByNumeroAffectation(numeroAffectation);
        if (affectationEntity != null){
            System.out.println("Dans couche infrastructure---chercheAffectationParNumeroAffectation different null");
            return affectationMapper.mapToDomain(affectationEntity);}
        else {
            System.out.println("Dans couche infrastructure---chercheAffectationParNumeroAffectation égal à null");
             throw new NotFoundException("L'affectation avec le numéro suivant n'existe pas " + numeroAffectation);
        }
    }

    @Override
    public void miseAjourAffectation(Affectation affectation) {

        AffectationEntity affectationEntity = iRepositoryJpaAffectation.findByNumeroAffectation(affectation.getNumeroAffectation());
        if (affectationEntity != null){
            System.out.println("Dans couche infrastructure---miseAjourAffectation different null");
            System.out.println("Dans couche infrastructure---affectation.getDateFin() " + affectation.getDateFin());
            System.out.println("Dans couche infrastructure---affectation.getCommentaire() " + affectation.getCommentaire());
            System.out.println("Dans couche infrastructure---affectation.getMotifFin() " + affectation.getMotifFin());
//            affectationEntity.setDateFin(affectation.getDateFin());
//            affectationEntity.setCommentaire(affectation.getCommentaire());
//            affectationEntity.setMotifFin(affectation.getMotifFin());
//
            CollaborateurEntity collaborateurEntity = affectationEntity.getCollaborateur();
            collaborateurEntity.setNumeroLigne(affectation.getCollaborateur().getNumeroLigne());
            IphoneEntity iphoneEntity = affectationEntity.getIphone();
            iphoneEntity.setEtatIphone(affectation.getIphone().getEtatIphone());

//            CollaborateurEntity collaborateurEntity = iRepositoryJpaCollaborateur.findByUid(affectation.getCollaborateur().getUid());
//            collaborateurEntity.setNumeroLigne(affectation.getCollaborateur().getNumeroLigne());
//
//            IphoneEntity iphoneEntity = iRepositoryJpaIphone.findByNumeroSerie(affectation.getIphone().getNumeroSerie());
//            iphoneEntity.setEtatIphone(affectation.getIphone().getEtatIphone());

            affectationEntity.setCollaborateur(collaborateurEntity);
            affectationEntity.setIphone(iphoneEntity);
            affectationEntity.setNumeroAffectation(affectation.getNumeroAffectation());
            affectationEntity.setCommentaire(affectation.getCommentaire());
            affectationEntity.setDateAffectation(affectation.getDateAffectation());
            affectationEntity.setDateFin(affectation.getDateFin());
            affectationEntity.setMotifFin(affectation.getMotifFin());
            affectationEntity.setDateRenouvellementPrevue(affectation.getDateRenouvellementPrevue());

             iRepositoryJpaAffectation.save(affectationEntity);
        }
        else {
            System.out.println("Dans couche infrastructure---miseAjourAffectation égal à null");
            throw new NotFoundException("L'affectation avec le numéro suivant n'existe pas " + affectation.getNumeroAffectation());
        }
    }

    public void supprimerAffectation(Affectation affectationASupprimer) {

        AffectationEntity affectationEntityASupprimer = affectationMapper.mapToEntity(affectationASupprimer);

        AffectationEntity affectationEntityEnTable = iRepositoryJpaAffectation.findByNumeroAffectation(affectationASupprimer.getNumeroAffectation());

        if (affectationEntityEnTable != null) {
            affectationEntityASupprimer.getCollaborateur().setCollaborateurId(affectationEntityEnTable.getCollaborateur().getCollaborateurId());
            affectationEntityASupprimer.getIphone().setIphoneId(affectationEntityEnTable.getIphone().getIphoneId());
            affectationEntityASupprimer.setId(affectationEntityEnTable.getId());
            iRepositoryJpaAffectation.delete(affectationEntityASupprimer);
        }
        else {
            throw new NotFoundException("L'affectation avec le numéro suivant n'existe pas " + affectationEntityASupprimer.getNumeroAffectation());
        }
    }



    @Override
    public List<Affectation> rechercheAffectationByUid(String collaborateurUid) {

        List<AffectationEntity> affectationsList = iRepositoryJpaAffectation.findByCollaborateurUid(collaborateurUid);

        return affectationMapper.mapToDomainList(affectationsList);
    }


    @Override
    public List<Affectation> rechercheAffectationAvecFiltres(FiltresAffectation filtresAffectation) {

        StringBuilder query = new StringBuilder();
        query.append("select a from AffectationEntity a where 1=1 ");

        String uid = filtresAffectation.getUid();
        if (uid != null && !uid.isEmpty()){
            query.append(String.format("AND a.collaborateur.uid = '%s' ", uid ));

        }

        String nom = filtresAffectation.getNom();
        if (nom != null && !nom.isEmpty()){
            query.append(String.format("AND a.collaborateur.nom = '%s' ", nom ));
        }

        String codeUo = filtresAffectation.getCodeUo();
        if (codeUo != null && !codeUo.isEmpty()){
            query.append(String.format("AND a.collaborateur.uo.codeUo = '%s' ",  codeUo));

        }

        String nomUsageUo = filtresAffectation.getNomUsageUo();
        if (nomUsageUo != null && !nomUsageUo.isEmpty()){
            query.append(String.format("AND a.collaborateur.uo.nomUsageUo = '%s' ", nomUsageUo));

        }

        String nomSite = filtresAffectation.getNomSite();
        if (nomSite != null && !nomSite.isEmpty()){
            query.append(String.format("AND a.collaborateur.uo.siteExercice.nomSite = '%s' ", nomSite ));

        }

        String numeroLigneCollaborateur = filtresAffectation.getNumeroLigneCollaborateur();
        if (numeroLigneCollaborateur != null && !numeroLigneCollaborateur.isEmpty()){
            query.append(String.format("AND a.collaborateur.numeroLigne = '%s' ", numeroLigneCollaborateur));

        }
        String nomModeleIphone = filtresAffectation.getNomModeleIphone();
        if (nomModeleIphone != null && !nomModeleIphone.isEmpty()){
            query.append(String.format("AND a.iphone.modeleIphoneEntity.nomModele = '%s' ", nomModeleIphone));
        }

        LocalDate dateRenouvMin = filtresAffectation.getDateRenouvMin();
        if (dateRenouvMin != null){
            query.append("AND a.dateRenouvellementPrevue > '" + dateRenouvMin + "' ");
        }

        LocalDate dateRenouvMax = filtresAffectation.getDateRenouvMax();
        if (dateRenouvMax != null){
            query.append("AND a.dateRenouvellementPrevue < '" + dateRenouvMax + "' ");
        }


        String maRequeteConstruite = query.toString();
        monLogger.debug(maRequeteConstruite);

        List<AffectationEntity> maListEntity = monEntityManager.createQuery(maRequeteConstruite).getResultList();
        List<Affectation> maList = new ArrayList<>();
        for (AffectationEntity affectationEntity : maListEntity) {
            maList.add(affectationMapper.mapToDomain(affectationEntity));
        }

        return maList;
    }


}
