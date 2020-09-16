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
import java.lang.invoke.SwitchPoint;
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

        AffectationEntity affectationEntity = affectationEntityMapper.mapToEntity(affectationACreer);

        iRepositoryJpaAffectation.save(affectationEntity);
    }
    @Override
    public Affectation chercheAffectationParNumeroAffectation(Long numeroAffectation) {

        AffectationEntity affectationEntity = iRepositoryJpaAffectation.findByNumeroAffectation(numeroAffectation);
        if (affectationEntity != null){
            monLogger.debug("couche infra chercheAffectationParNumeroAffectation affectationEntity !=null  = {}");
            return affectationMapper.mapToDomain(affectationEntity);}
        else {
            monLogger.debug("couche infra chercheAffectationParNumeroAffectation affectationEntity =null  = {}");
            throw new NotFoundException("L'affectation avec le numéro suivant n'existe pas " + numeroAffectation);
        }
    }

    @Override
    public void miseAjourAffectation(Affectation affectation) {

        AffectationEntity affectationEntity = iRepositoryJpaAffectation.findByNumeroAffectation(affectation.getNumeroAffectation());
        monLogger.debug("couche infra miseAjourAffectation---");

        if (affectationEntity != null){

             AffectationEntity affectationEntityRetourMapper = affectationEntityMapper.mapToEntity(affectation);

             affectationEntityRetourMapper.setId(affectationEntity.getId());
             iRepositoryJpaAffectation.save(affectationEntityRetourMapper);
        }
        else {
            System.out.println("Dans couche infrastructure---miseAjourAffectation égal à null");
            throw new NotFoundException("L'affectation avec le numéro suivant n'existe pas " + affectation.getNumeroAffectation());
        }
    }

    @Override
    public Affectation supprimerAffectation(Affectation affectationASupprimer) {

        monLogger.debug("supprimerAffectation" + affectationASupprimer);
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
        return affectationASupprimer;
    }

    @Override
    public List<Affectation> rechercheAffectationByUid(String collaborateurUid) {

        List<AffectationEntity> affectationsList = iRepositoryJpaAffectation.findByCollaborateurUid(collaborateurUid);

        return affectationMapper.mapToDomainList(affectationsList);
    }


    @Override
    public List<Affectation> rechercheAffectationAvecFiltres(FiltresAffectation filtresAffectation) {
        int numeroPageDemande;
        int taillePage;

        if (filtresAffectation.getNumeroDePage() != 0) {
            numeroPageDemande = filtresAffectation.getNumeroDePage();
        } else {
            numeroPageDemande = 1;
        }
        if (filtresAffectation.getTaillePage() != 0) {
            taillePage = filtresAffectation.getTaillePage();
        } else {
            taillePage = 10;
        }
        int positionDeDepart = (numeroPageDemande-1)*taillePage;

//        StringBuilder sortQueryDesc = new StringBuilder();
//        sortQueryDesc.append("order by ");
        StringBuilder query = new StringBuilder();
        query.append("select a from AffectationEntity a where 1=1 ");

        String uid = filtresAffectation.getUid();
        if (uid != null && !uid.isEmpty()){
            query.append(String.format("AND a.collaborateur.uid = '%s' ", uid ));
//            sortQueryDesc.append(String.format(uid ,"desc"));
            System.out.println("couche infrastructure----query sur uid---" + query);
        }

        String nom = filtresAffectation.getNom();
        if (nom != null && !nom.isEmpty()){
            query.append(String.format("AND a.collaborateur.nom = '%s' ", nom ));
            System.out.println("couche infrastructure----query sur nom---" + query);
        }

        String codeUo = filtresAffectation.getCodeUo();
        if (codeUo != null && !codeUo.isEmpty()){
            query.append(String.format("AND a.collaborateur.uo.codeUo = '%s' ",  codeUo));
            System.out.println("couche infrastructure----query sur codeUo---" + query);
        }

        String nomUsageUo = filtresAffectation.getNomUsageUo();
        if (nomUsageUo != null && !nomUsageUo.isEmpty()){
            query.append(String.format("AND a.collaborateur.uo.nomUsageUo = '%s' ", nomUsageUo));
            System.out.println("couche infrastructure----query sur nomUsage---" + query);

        }

        String nomSite = filtresAffectation.getNomSite();
        if (nomSite != null && !nomSite.isEmpty()){
            query.append(String.format("AND a.collaborateur.uo.siteExercice.nomSite = '%s' ", nomSite ));
            System.out.println("couche infrastructure----query sur nomSite---" + query);
        }

        String numeroLigneCollaborateur = filtresAffectation.getNumeroLigneCollaborateur();
        if (numeroLigneCollaborateur != null && !numeroLigneCollaborateur.isEmpty()){
            query.append(String.format("AND a.collaborateur.numeroLigne = '%s' ", numeroLigneCollaborateur));
            System.out.println("couche infrastructure----query sur numeroLigne---" + query);

        }
        String nomModeleIphone = filtresAffectation.getNomModeleIphone();
        if (nomModeleIphone != null && !nomModeleIphone.isEmpty()){
            query.append(String.format("AND a.iphone.modeleIphoneEntity.nomModele = '%s' ", nomModeleIphone));
            System.out.println("couche infrastructure----query sur nomModele---" + query);
        }

        LocalDate dateRenouvMin = filtresAffectation.getDateRenouvMin();
        if (dateRenouvMin != null){
            query.append("AND a.dateRenouvellementPrevue > '" + dateRenouvMin + "' ");
            System.out.println("couche infrastructure----query sur dateRenouvellement >=---" + query);
        }

        LocalDate dateRenouvMax = filtresAffectation.getDateRenouvMax();
        if (dateRenouvMax != null){
            query.append("AND a.dateRenouvellementPrevue < '" + dateRenouvMax + "' ");
            System.out.println("couche infrastructure----query sur dateRenouvellement =<---" + query);
        }


        if (filtresAffectation.getCritereDeTri() != null) {
            String critereDeTri;
            switch (filtresAffectation.getCritereDeTri()) {
                //A completer avec les différents critère de tri envisagés
                case "UID":
                    critereDeTri = "a.collaborateur.uid";
                    break;
                case "DATE_AFFECTATION":
                    critereDeTri = "a.dateAffectation";
                    break;
                default:
                    critereDeTri = null;
                    break;
            }

            String sensDuTri;
            if (filtresAffectation.getSensduTri() != null && filtresAffectation.getSensduTri().equals("D")) {
                sensDuTri = "DESC";
            } else {
                sensDuTri = "ASC";
            }
            if (critereDeTri != null && !critereDeTri.isEmpty()) {
                query.append("ORDER BY " + critereDeTri + " " + sensDuTri);
            }
        }


        String maRequeteConstruite = query.toString();
        System.out.println("couche infrastructure----query sur maRequeteConstruite  ----" + maRequeteConstruite );
        monLogger.debug(maRequeteConstruite);

        List<AffectationEntity> maListEntity = monEntityManager.createQuery(maRequeteConstruite)
                .setMaxResults(taillePage)
                .setFirstResult(positionDeDepart)
                .getResultList();
        List<Affectation> maList = new ArrayList<>();
        for (AffectationEntity affectationEntity : maListEntity) {
            maList.add(affectationMapper.mapToDomain(affectationEntity));
        }
        System.out.println("couche infrastructure ----- query de fin maList---" + maList);
        return maList;
    }


}
