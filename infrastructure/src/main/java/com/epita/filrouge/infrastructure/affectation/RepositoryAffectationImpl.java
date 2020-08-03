package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.iphone.IphoneEntity;
import com.epita.filrouge.infrastructure.iphone.ModeleIphoneEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositoryAffectationImpl implements IRepositoryAffectation {

    @Autowired
    private AffectationEntityMapper affectationMapper;

    @Autowired
    IRepositoryJpaAffectation iRepositoryJpaAffectation;

    @PersistenceContext
    EntityManager monEntityManager;

    @Override
    public void affecter(Affectation affectationACreer) {

        System.out.println("affectationACreer = " + affectationACreer);
        Collaborateur collaborateur = affectationACreer.getCollaborateur();
        CollaborateurEntity collaborateurEntity = new CollaborateurEntity();
        collaborateurEntity.setCollaborateurId(collaborateur.getId());
        collaborateurEntity.setUid(collaborateur.getUid());
        collaborateurEntity.setNom(collaborateur.getNom());
        collaborateurEntity.setPrenom(collaborateur.getPrenom());
        collaborateurEntity.setNumeroLigne(collaborateur.getNumeroLigne());

        System.out.println("collaborateurEntity.getCollaborateurId() = " + collaborateurEntity.getCollaborateurId());
        System.out.println("collaborateurEntity.getUid() = " + collaborateurEntity.getUid());
        System.out.println("collaborateurEntity.getNom() = " + collaborateurEntity.getNom());
        System.out.println("collaborateurEntity.getPrenom() = " + collaborateurEntity.getPrenom());
        System.out.println("collaborateurEntity.getCollaborateurId() = " + collaborateurEntity.getCollaborateurId());

        Iphone iPhone = affectationACreer.getIphone();
        ModeleIphone modeleIphone = iPhone.getModeleIphone();

        ModeleIphoneEntity modeleIphoneEntity = new ModeleIphoneEntity();
        modeleIphoneEntity.setNomModele(modeleIphone.getNomModele());
        modeleIphoneEntity.setModeleId(modeleIphone.getModeleID());
        System.out.println("infra modeleIphoneEntity.getModeleId() = " + modeleIphoneEntity.getModeleId());
        System.out.println("infra modeleIphoneEntity.getNomModele() = " + modeleIphoneEntity.getNomModele());


        IphoneEntity iphoneEntity = new IphoneEntity();
        iphoneEntity.setIphoneId(iPhone.getIphoneId());
        iphoneEntity.setNumeroSerie(iPhone.getNumeroSerie());
        iphoneEntity.setEtatIphone(iPhone.getEtatIphone());
        iphoneEntity.setModeleIphoneEntity(modeleIphoneEntity);
        System.out.println("iphoneEntity.getIphoneId() = " + iphoneEntity.getIphoneId());
        System.out.println("iphoneEntity.getEtatIphone() = " + iphoneEntity.getEtatIphone());
        System.out.println("iphoneEntity.getNumeroSerie() = " + iphoneEntity.getNumeroSerie());

        AffectationEntity affectationEntity = new AffectationEntity();

        affectationEntity.setNumeroAffectation(affectationACreer.getNumeroAffectation());
        affectationEntity.setDateAffectation(affectationACreer.getDateAffectation());
        affectationEntity.setDateRenouvellementPrevue(affectationACreer.getDateRenouvellementPrevue());
        affectationEntity.setDateFin(affectationACreer.getDateFin());
        affectationEntity.setCommentaire(affectationACreer.getCommentaire());
        affectationEntity.setMotifFin(affectationACreer.getMotifFin());
        affectationEntity.setCollaborateur(collaborateurEntity);
        affectationEntity.setIphone(iphoneEntity);

        iRepositoryJpaAffectation.save(affectationEntity);
    }

    @Override
    public List<Affectation> listerAffectation() {
        return affectationMapper.mapToDomainList(iRepositoryJpaAffectation.findAll());
    }

    @Override
    public List<Affectation> rechercheAffectationAvecFiltres(String uid, String nom, String codeUo, String nomUsageUo, String nomSite, String numeroLigneCollaborateur, String nomModeleIphone, LocalDate dateRenouvMin, LocalDate dateRenouvMax) {
        StringBuilder query = new StringBuilder();
        query.append("select * from affectation where 1=1 ");
        if (uid != null | uid !=""){
            query.append("AND Affectation.Collaborateur.uid = {} " + uid);
        }
        if (nom != null | nom !=""){
            query.append("AND Affectation.Collaborateur.nom = {} " + nom);
        }
        if (codeUo != null | codeUo !=""){
            query.append("AND Affectation.Collaborateur.uo.codeuo = {} " + codeUo);
        }
        if (nomUsageUo != null | nomUsageUo !=""){
            query.append("AND affectation.collaborateur.uo.nomusageuo = {} " + nomUsageUo);
        }
        if (nomSite != null | nomSite !=""){
            query.append("AND affectation.collaborateur.uo.site.nomusagesite = {} " + nomSite);
        }
        if (numeroLigneCollaborateur != null | numeroLigneCollaborateur !=""){
            query.append("AND affectation.collaborateur.numeroligne = {} " + numeroLigneCollaborateur);
        }
        if (nomModeleIphone != null | nomModeleIphone !=""){
            query.append("AND affectation.iphone.modeleiphone.nommodele = {} " + nomModeleIphone);
        }
        if (dateRenouvMin != null){
            query.append("AND affectation.dateRenouvellementPrevue > {} " + dateRenouvMin);
        }
        if (dateRenouvMax != null){
            query.append("AND affectation.dateRenouvellementPrevue < {} " + dateRenouvMax);
        }

        String maRequete = query.toString();

        List<AffectationEntity> maListEntity = monEntityManager.createQuery(maRequete).getResultList();
        List<Affectation> maList = new ArrayList<>();
        for (AffectationEntity affectationEntity : maListEntity) {
            maList.add(affectationMapper.mapToDomain(affectationEntity));
        }

        return maList;
    }

//    @Override
//    public List<Affectation> rechercheAffectationByUid(String collaborateurUid) {
//        List<AffectationEntity> affectationsList = iRepositoryJpaAffectation.findByCollabarateurEntityUid(collaborateurUid);
//        if (affectationsList.size() != 0) {
//            if (affectationsList.get)
//            return null; // à compléter ********************************
//        } else
//            throw new NotFoundException("AFFECTATION001", "pas d'affectation pour Uid suivant : " + collaborateurUid);
//    }

}
