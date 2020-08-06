package com.epita.filrouge.application.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.collaborateur.IRepositoryCollaborateur;
import com.epita.filrouge.domain.exception.AllReadyExistException;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.IRepositoryIphone;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.uo.IRepositoryUo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import java.time.LocalDate;

@Service
public class AffectationManagementImpl implements IAffectationManagement {

    private static final EtatIphoneEnum etatIphoneEnum = EtatIphoneEnum.AFFECTE;

    Logger monLogger = LoggerFactory.getLogger(AffectationManagementImpl.class);

    @Autowired
    private IRepositoryAffectation repositoryAffectation;

    @Autowired
    private IRepositoryCollaborateur repositoryCollaborateur;

    @Autowired
    private IRepositoryIphone repositoryIphone;

    @Override
    public Affectation creerAffectation(String collaborateurUid, String iPhoneNumeroSerie, LocalDate dateAffectation, String numeroLigne, String commentaire) {

        monLogger.debug("creer affectation--collaborateurUid");
        Collaborateur collaborateur = repositoryCollaborateur.findByUid(collaborateurUid);

        monLogger.debug("creer affectation--collaborateur.getCollaborateur().getUid---- {}" , collaborateur.getUid());

        monLogger.debug("application collaborateur.getPrenom() = {} " , collaborateur.getPrenom());
//        if (user == null) {
//            throw new NotFoundException(USER_NOT_FOUND, "Email " + userName + " not found");
//        }

        // implementation du contrôle de l'existence de cet UID dans la table des affectations car si déjà présent et affectation toujours en cours,
        // on va obliger à clôturer avant de resaisir

        List<Affectation> affectationDejaCree = repositoryAffectation.rechercheAffectationByUid(collaborateurUid);

        if (affectationDejaCree != null) {
            //faire la boucle for pour test de la date de fin. Si la date de fin est à NULL, l'affectation existe déjà donc refuser la création

            for (final Affectation affectations : affectationDejaCree) {
                if (affectations.getDateFin() == null) {
                    throw new AllReadyExistException("L'affectation pour ce collaborateur existe déjà, merci de la clôturer au préalable : " + affectations.getCollaborateur().getUid());
                }
            }
        }

//        Iphone iPhone = repositoryIphone.findByNumeroSerie(iPhoneNumeroSerie);
        Iphone iPhone = repositoryIphone.rechercheIphoneParNumeroSerie(iPhoneNumeroSerie);
        monLogger.debug("application iPhone.getIphoneId() = {}}" ,iPhone.getIphoneId());
        System.out.println("application iPhone.getIphoneId() = " + iPhone.getIphoneId());
//      test existence Iphone
        Long numeroAffectation = genererNumeroAffectation();
        Affectation affectationACreer = new Affectation(numeroAffectation, dateAffectation, commentaire,collaborateur, iPhone);

        repositoryAffectation.affecter(affectationACreer);

        repositoryCollaborateur.miseAJourCollaborateur(collaborateur, numeroLigne);

//        iPhone.setEtatIphone(EtatIphoneEnum.AFFECTE);
//
//  //        //iphone.setEtat(..) => la recherche a déjà été faite
////        repositoryIphone.update(iphone);
//       // implementation de la mise à jour générique sur le téléphone en modifiant l'attribut concerné avant

        repositoryIphone.miseAJourEtatIphone(iPhoneNumeroSerie, etatIphoneEnum);

        return affectationACreer;

    }

    private static Long genererNumeroAffectation() {
        final Random random = new Random();
        return random.nextLong();
    }

    @Override
    public List<Affectation> listerAffectation() {
        return repositoryAffectation.listerAffectation();
    }

    @Override
    public List<Affectation> listerAffection(String uid, String nom, String codeUo, String nomUsageUo, String nomSite, String numeroLigneCollaborateur, String nomModeleIphone, LocalDate dateRenouvMin, LocalDate dateRenouvMax) {

        return repositoryAffectation.rechercheAffectationAvecFiltres(uid,nom,codeUo,nomUsageUo,nomSite,numeroLigneCollaborateur,nomModeleIphone,dateRenouvMin,dateRenouvMax);
    }
}
