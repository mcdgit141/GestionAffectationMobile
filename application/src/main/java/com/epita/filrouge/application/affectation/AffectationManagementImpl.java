package com.epita.filrouge.application.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.AffectationNumeroGenerateur;
import com.epita.filrouge.domain.affectation.FiltresAffectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.collaborateur.IRepositoryCollaborateur;
import com.epita.filrouge.domain.exception.AllReadyClotureeException;
import com.epita.filrouge.domain.exception.AllReadyExistException;
import com.epita.filrouge.domain.exception.BadRequestException;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.IRepositoryIphone;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Affectation creerAffectation(String collaborateurUid, String iPhoneNumeroSerie, LocalDate dateAffectation, String numeroLigne, String commentaire) throws AllReadyExistException {

        monLogger.debug("creer affectation--collaborateurUid");
        Collaborateur collaborateur = repositoryCollaborateur.findByUid(collaborateurUid);

        List<Affectation> affectationDejaCree = repositoryAffectation.rechercheAffectationByUid(collaborateurUid);

        monLogger.debug("creer affectation--collaborateur.getCollaborateur().getUid---- {}", collaborateur.getUid());
        monLogger.debug("application collaborateur.getPrenom() = {} ", collaborateur.getPrenom());

        // implementation du contrôle de l'existence de cet UID dans la table des affectations car si déjà présent et affectation toujours en cours,
        // on va obliger à clôturer avant de resaisir

        /*controlCollaborateurEstSansAffectationEnCours(collaborateurUid);*/ // MCD changement mode domain en date du 17/08/2020

//      test existence Iphone
        Iphone iPhone = repositoryIphone.rechercheIphoneParNumeroSerie(iPhoneNumeroSerie);
        /*controlDisponibiliteIphone(iPhone);*/ // MCD changement mode domain en date du 17/08/2020

        monLogger.debug("application iPhone.getIphoneId() = {}}", iPhone.getIphoneId());

//        Long numeroAffectation = genererNumeroAffectation();
        Affectation affectationACreer = new Affectation(AffectationNumeroGenerateur.genererNumeroAffectation(), dateAffectation, commentaire, collaborateur, iPhone);

        Affectation affectationACreerFinal = affectationACreer.reglesAppliqueesPourLaCreation(collaborateur,iPhone,numeroLigne, affectationDejaCree);

        repositoryAffectation.affecter(affectationACreerFinal);
        /* repositoryAffectation.affecter(affectationACreer);*/ // MCD changement mode domain en date du 17/08/2020

        /*repositoryCollaborateur.miseAJourCollaborateur(collaborateur, numeroLigne);*/ // MCD changement mode domain en date du 17/08/2020

//       // implementation de la mise à jour générique sur le téléphone en modifiant l'attribut concerné avant

        /*repositoryIphone.miseAJourEtatIphone(iPhoneNumeroSerie, etatIphoneEnum);*/ // MCD changement mode domain en date du 17/08/2020

        /*return affectationACreer;*/// MCD changement mode domain en date du 17/08/2020
        return affectationACreerFinal;
    }

//    private void controlDisponibiliteIphone(Iphone iPhone) {
//        if (iPhone.getEtatIphone() != EtatIphoneEnum.DISPONIBLE) {
//            throw new AllReadyExistException("Cet iPhone n'est pas disponible, merci de recommencer : " + iPhone.getNumeroSerie());
//        }
//    }

////    private void controlCollaborateurEstSansAffectationEnCours(String collaborateurUid) {
////        List<Affectation> affectationDejaCree = repositoryAffectation.rechercheAffectationByUid(collaborateurUid);
////
////        if (affectationDejaCree != null) {
////            //faire la boucle for pour test de la date de fin. Si la date de fin est à NULL, l'affectation existe déjà donc refuser la création
////
////            for (final Affectation affectations : affectationDejaCree) {
////                if (affectations.getDateFin() == null) {
////                    throw new AllReadyExistException("L'affectation pour ce collaborateur existe déjà, merci de la clôturer au préalable : " + affectations.getCollaborateur().getUid());
////                }
////            }
////        }
//    }

//    private static Long genererNumeroAffectation() {
//        final Random random = new Random();
//        return random.nextLong();
//    }

    @Override
    @Transactional
    public void cloturerAffectation(Long numeroAffectation, String affectationCommentaire, String motifFin, LocalDate dateFin) throws NotFoundException, AllReadyClotureeException {

         switch (motifFin.toUpperCase()){
            case "PERDU":
                  motifFin = "PERDU";
                  break;
            case "VOLE":
                  motifFin = "VOLE";
                  break;
            case "CASSE":
                motifFin = "CASSE";
                 break;
            case "RESTITUE":
                motifFin = "RESTITUE";
                break;
            default:
                throw new BadRequestException("Le motif de fin mentionné n'est pas conforme aux valeurs attendues, veuillez corriger la saisie");
        }

        Affectation affectationACloturer = repositoryAffectation.chercheAffectationParNumeroAffectation(numeroAffectation);
        Collaborateur collaborateur = affectationACloturer.getCollaborateur();
        Iphone iphone = affectationACloturer.getIphone();

//        Collaborateur collaborateur = repositoryCollaborateur.findByUid(affectationACloturer.getCollaborateur().getUid());
//        Collaborateur collaborateurAMettreAJourSuiteClotureAffectation = collaborateur.miseAJourCollaborateurSuiteClotureAffectation();
//
//        Iphone iphone = repositoryIphone.rechercheIphoneParNumeroSerie(affectationACloturer.getIphone().getNumeroSerie());
//        Iphone iphoneAMettreAJourSuiteClotureAffectation = iphone.miseAJourIphoneSuiteClotureAffectation();

        Affectation affectationACloturerFinal = affectationACloturer.reglesAppliqueesPourCloturerAffectation(collaborateur,
                iphone, affectationCommentaire, motifFin, dateFin);

        System.out.println("couche application -cloturerAffectation--affectationAcloturer.getNumeroAffectation---" + affectationACloturer.getNumeroAffectation());
        System.out.println("couche application -cloturerAffectation--dateFin---" + dateFin);

//        if (dateFin != null)
//            {affectationACloturer.setDateFin(dateFin);}
//        else
//            {affectationACloturer.setDateFin(LocalDate.now());}
//
//        affectationACloturer.setMotifFin(motifFin);
//        affectationACloturer.setCommentaire(affectationCommentaire);

        repositoryAffectation.miseAjourAffectation(affectationACloturerFinal);

//        return affectationACloturerFinal;

    }

    @Override
    public List<Affectation> listerAffectation(FiltresAffectation filtresAffectation) {

        return repositoryAffectation.rechercheAffectationAvecFiltres(filtresAffectation);
//        return null;
        }
    }

