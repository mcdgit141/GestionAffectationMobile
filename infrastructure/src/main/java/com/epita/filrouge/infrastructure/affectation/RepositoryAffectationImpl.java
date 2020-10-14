package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.FiltresAffectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.infrastructure.collaborateur.IRepositoryJpaCollaborateur;
import com.epita.filrouge.infrastructure.iphone.IRepositoryJpaIphone;
import com.epita.filrouge.infrastructure.uo.UoEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.*;

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

        monLogger.debug("affectationACreer = {}", affectationACreer);

        AffectationEntity affectationEntity = affectationEntityMapper.mapToEntity(affectationACreer);

        iRepositoryJpaAffectation.save(affectationEntity);
    }

    @Override
    public Affectation chercheAffectationParNumeroAffectation(Long numeroAffectation) {

        AffectationEntity affectationEntity = iRepositoryJpaAffectation.findByNumeroAffectation(numeroAffectation);
        if (affectationEntity != null) {
            return affectationMapper.mapToDomain(affectationEntity);
        } else {
            monLogger.debug("couche infra chercheAffectationParNumeroAffectation, affectationEntity non trouvée en base  = {}", numeroAffectation);
            throw new NotFoundException("L'affectation avec le numéro suivant n'existe pas " + numeroAffectation);
        }
    }

    @Override
    public void miseAjourAffectation(Affectation affectation) {

        AffectationEntity affectationEntity = iRepositoryJpaAffectation.findByNumeroAffectation(affectation.getNumeroAffectation());
        monLogger.debug("couche infra miseAjourAffectation---");

        if (affectationEntity != null) {

            AffectationEntity affectationEntityRetourMapper = affectationEntityMapper.mapToEntity(affectation);

            affectationEntityRetourMapper.setId(affectationEntity.getId());
            iRepositoryJpaAffectation.save(affectationEntityRetourMapper);
        } else {
            monLogger.debug("couche infra miseAjourAffectation, affectationEntity non trouvée en base  = {}", affectation.getNumeroAffectation());
            throw new NotFoundException("L'affectation avec le numéro suivant n'existe pas " + affectation.getNumeroAffectation());
        }
    }

    @Override
    public Affectation supprimerAffectation(Affectation affectationASupprimer) {

        AffectationEntity affectationEntityASupprimer = affectationMapper.mapToEntity(affectationASupprimer);

        AffectationEntity affectationEntityEnTable = iRepositoryJpaAffectation.findByNumeroAffectation(affectationASupprimer.getNumeroAffectation());

        if (affectationEntityEnTable != null) {
            affectationEntityASupprimer.getCollaborateur().setCollaborateurId(affectationEntityEnTable.getCollaborateur().getCollaborateurId());
            affectationEntityASupprimer.getIphone().setIphoneId(affectationEntityEnTable.getIphone().getIphoneId());
            affectationEntityASupprimer.setId(affectationEntityEnTable.getId());
            iRepositoryJpaAffectation.delete(affectationEntityASupprimer);
        } else {
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
    public AbstractMap.Entry<List<Integer>, List<Affectation>> rechercheAffectationAvecFiltres2(FiltresAffectation filtresAffectation) {

        Integer nombreEnregistrementsTotal = this.rechercheNombreEnregistrements(filtresAffectation);

        if (filtresAffectation.getNumeroDePage() == 0) {
            filtresAffectation.setNumeroDePage(1);
        }
        if (filtresAffectation.getTaillePage() == 0) {
            filtresAffectation.setTaillePage(10);
        }

        int nombreDePages = nombreEnregistrementsTotal / filtresAffectation.getTaillePage() +
                            (nombreEnregistrementsTotal % filtresAffectation.getTaillePage() == 0 ? 0 : 1);
        List<Integer> listeMeta = new ArrayList<>();
        listeMeta.add(nombreEnregistrementsTotal);
        listeMeta.add(nombreDePages);
        listeMeta.add(filtresAffectation.getNumeroDePage());
        listeMeta.add(filtresAffectation.getTaillePage());



        List<Affectation> affectationList = this.rechercheAffectationAvecFiltres(filtresAffectation);


        return new AbstractMap.SimpleEntry<>(listeMeta, affectationList);
    }



    private Integer rechercheNombreEnregistrements(FiltresAffectation filtresAffectation) {
        StringBuilder queryCount = new StringBuilder();
        queryCount.append("select count(a) from AffectationEntity a where 1=1 ");

        String where = this.buildDeLaPartieWhere(filtresAffectation);
        queryCount.append(where);

        String maRequeteConstruite = queryCount.toString();
        return (int) (long) monEntityManager.createQuery(maRequeteConstruite)
                .getSingleResult();


    }



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

        StringBuilder query = new StringBuilder();
        query.append("select a from AffectationEntity a where 1=1 ");

        String where = this.buildDeLaPartieWhere(filtresAffectation);
        query.append(where);



        String sensDuTri;
        String critereDeTri;
        if (filtresAffectation.getCritereDeTri() != null) {
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

            if (filtresAffectation.getSensduTri() != null && filtresAffectation.getSensduTri().equals("D")) {
                sensDuTri = "DESC";
            } else {
                sensDuTri = "ASC";
            }
        } else {
            critereDeTri = "a.dateAffectation";
            sensDuTri = "DESC";
        }

        String triNumeroAffectation = " , a.numeroAffectation ASC";
        query.append("ORDER BY ")
                .append(critereDeTri)
                .append(" ")
                .append(sensDuTri)
                .append(triNumeroAffectation);


        String maRequeteConstruite = query.toString();
        monLogger.debug(maRequeteConstruite);

        List<AffectationEntity> maListEntity = monEntityManager.createQuery(maRequeteConstruite)
                .setMaxResults(taillePage)
                .setFirstResult(positionDeDepart)
                .getResultList();
        List<Affectation> maList = new ArrayList<>();
        for (AffectationEntity affectationEntity : maListEntity) {
            maList.add(affectationMapper.mapToDomain(affectationEntity));
        }

        return maList;
    }

    private String buildDeLaPartieWhere (FiltresAffectation filtresAffectation) {

        StringBuilder where = new StringBuilder();

        String uid = filtresAffectation.getUid();
        if (uid != null && !uid.isEmpty()){
            where.append(String.format("AND upper(a.collaborateur.uid) = '%s' ", uid.toUpperCase() ));
        }

        String nom = filtresAffectation.getNom();
        if (nom != null && !nom.isEmpty()){
            where.append(String.format("AND upper(a.collaborateur.nom) = '%s' ", nom.toUpperCase() ));
        }

        String codeUo = filtresAffectation.getCodeUo();
        if (codeUo != null && !codeUo.isEmpty()){
            where.append(String.format("AND upper(a.collaborateur.uo.codeUo) = '%s' ",  codeUo.toUpperCase()));
        }

        String nomUsageUo = filtresAffectation.getNomUsageUo();
        if (nomUsageUo != null && !nomUsageUo.isEmpty()){
            where.append(String.format("AND upper(a.collaborateur.uo.nomUsageUo) = '%s' ", nomUsageUo.toUpperCase()));

        }

        String nomSite = filtresAffectation.getNomSite();
        if (nomSite != null && !nomSite.isEmpty()){
            where.append(String.format("AND upper(a.collaborateur.uo.siteExercice.nomSite) = '%s' ", nomSite.toUpperCase() ));
        }

        String numeroLigneCollaborateur = filtresAffectation.getNumeroLigneCollaborateur();
        if (numeroLigneCollaborateur != null && !numeroLigneCollaborateur.isEmpty()){
            where.append(String.format("AND a.collaborateur.numeroLigne = '%s' ", numeroLigneCollaborateur));

        }
        String nomModeleIphone = filtresAffectation.getNomModeleIphone();
        if (nomModeleIphone != null && !nomModeleIphone.isEmpty()){
            where.append(String.format("AND upper(a.iphone.modeleIphoneEntity.nomModele) = '%s' ", nomModeleIphone.toUpperCase()));
        }

        LocalDate dateRenouvMin = filtresAffectation.getDateRenouvMin();
        if (dateRenouvMin != null){
            where.append("AND a.dateRenouvellementPrevue > '" + dateRenouvMin + "' ");
        }

        LocalDate dateRenouvMax = filtresAffectation.getDateRenouvMax();
        if (dateRenouvMax != null){
            where.append("AND a.dateRenouvellementPrevue < '" + dateRenouvMax + "' ");
        }

        return where.toString();
    }
}
