package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.FiltresAffectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
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

        affectationEntity.setNumeroAffectation(affectationACreer.getNumeroAffectation());
        affectationEntity.setDateAffectation(affectationACreer.getDateAffectation());
        affectationEntity.setDateRenouvellementPrevue(affectationACreer.getDateRenouvellementPrevue());
        affectationEntity.setDateFin(affectationACreer.getDateFin());
        affectationEntity.setCommentaire(affectationACreer.getCommentaire());
        affectationEntity.setMotifFin(affectationACreer.getMotifFin());
        affectationEntity.setCollaborateur(monCollaborateurEntity);
        affectationEntity.setIphone(monIphoneEntity);

        iRepositoryJpaAffectation.save(affectationEntity);
    }


//    @Override
//    public List<Affectation> listerAffectation() {
//        return affectationMapper.mapToDomainList(iRepositoryJpaAffectation.findAll());
//    }

    @Override
    public List<Affectation> rechercheAffectationByUid(String collaborateurUid) {

        List<AffectationEntity> affectationsList = iRepositoryJpaAffectation.findByCollaborateurUid(collaborateurUid);

        return affectationMapper.mapToDomainList(affectationsList);
    }

//    @Override
//    public List<Affectation> rechercheAffectationAvecFiltres(String uid, String nom, String codeUo, String nomUsageUo, String nomSite, String numeroLigneCollaborateur, String nomModeleIphone, LocalDate dateRenouvMin, LocalDate dateRenouvMax) {
//        StringBuilder query = new StringBuilder();
//        query.append("select a from AffectationEntity a where 1=1 ");
//
//        if (uid != null && !uid.isEmpty()){
//            query.append(String.format("AND a.collaborateur.uid = '%s' ", uid ));
//
//        }
//        if (nom != null && !nom.isEmpty()){
//            query.append(String.format("AND a.collaborateur.nom = '%s' ", nom ));
//        }
//        if (codeUo != null && !codeUo.isEmpty()){
//            query.append(String.format("AND a.collaborateur.uo.codeUo = '%s' ",  codeUo));
//
//        }
//        if (nomUsageUo != null && !nomUsageUo.isEmpty()){
//            query.append(String.format("AND a.collaborateur.uo.nomUsageUo = '%s' ", nomUsageUo));
//
//        }
//        if (nomSite != null && !nomSite.isEmpty()){
//            query.append(String.format("AND a.collaborateur.uo.siteExercice.nomSite = '%s' ", nomSite ));
//
//        }
//        if (numeroLigneCollaborateur != null && !numeroLigneCollaborateur.isEmpty()){
//            query.append(String.format("AND a.collaborateur.numeroLigne = '%s' ", numeroLigneCollaborateur));
//
//        }
//        if (nomModeleIphone != null && !nomModeleIphone.isEmpty()){
//            query.append(String.format("AND a.iphone.modeleIphoneEntity.nomModele = '%s' ", nomModeleIphone));
//        }
//        if (dateRenouvMin != null){
//            query.append("AND a.dateRenouvellementPrevue > '" + dateRenouvMin + "' ");
//        }
//        if (dateRenouvMax != null){
//            query.append("AND a.dateRenouvellementPrevue < '" + dateRenouvMax + "' ");
//        }
//
//
//        String maRequeteConstruite = query.toString();
//        monLogger.debug(maRequeteConstruite);
//
//        List<AffectationEntity> maListEntity = monEntityManager.createQuery(maRequeteConstruite).getResultList();
//        List<Affectation> maList = new ArrayList<>();
//        for (AffectationEntity affectationEntity : maListEntity) {
//            maList.add(affectationMapper.mapToDomain(affectationEntity));
//        }
//
//        return maList;
//    }

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
