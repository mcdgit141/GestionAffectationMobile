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
        System.out.println("creer affectation--collaborateurUid---" + collaborateurUid);
        Collaborateur collaborateur = repositoryCollaborateur.findByUid(collaborateurUid);

        List<Affectation> affectationDejaCree = repositoryAffectation.rechercheAffectationByUid(collaborateurUid);

        monLogger.debug("creer affectation--collaborateur.getCollaborateur().getUid---- {}", collaborateur.getUid());
        monLogger.debug("application collaborateur.getPrenom() = {} ", collaborateur.getPrenom());

        Iphone iPhone = repositoryIphone.rechercheIphoneParNumeroSerie(iPhoneNumeroSerie);

        monLogger.debug("application iPhone.getIphoneId() = {}}", iPhone.getIphoneId());

        Affectation affectationACreer = new Affectation(AffectationNumeroGenerateur.genererNumeroAffectation(), dateAffectation, commentaire, collaborateur, iPhone);

        Affectation affectationACreerFinal = affectationACreer.reglesAppliqueesPourLaCreation(collaborateur,iPhone,numeroLigne, affectationDejaCree);

        repositoryAffectation.affecter(affectationACreerFinal);

        return affectationACreerFinal;
    }

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

        Affectation affectationACloturerFinal = affectationACloturer.reglesAppliqueesPourCloturerAffectation(collaborateur,
                iphone, affectationCommentaire, motifFin, dateFin);

        monLogger.debug("couche application -cloturerAffectation--affectationAcloturer.getNumeroAffectation---", affectationACloturer.getNumeroAffectation());
        monLogger.debug("couche application -cloturerAffectation--dateFin-----", dateFin);

        repositoryAffectation.miseAjourAffectation(affectationACloturerFinal);

    }

    @Override
    public void supprimerAffectation(Long numeroAffectation) {

        monLogger.debug("supprimerAffectation : numeroAffecation" + numeroAffectation);
        Affectation affectationASupprimer = repositoryAffectation.chercheAffectationParNumeroAffectation(numeroAffectation);

        affectationASupprimer.reglesAppliqueesPourSuppressionAffectation();

        repositoryAffectation.supprimerAffectation(affectationASupprimer);
    }

    @Override
    public List<Affectation> listerAffectation(FiltresAffectation filtresAffectation) {

        return repositoryAffectation.rechercheAffectationAvecFiltres(filtresAffectation);

        }
    }

