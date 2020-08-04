package com.epita.filrouge.application.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.collaborateur.IRepositoryCollaborateur;
import com.epita.filrouge.domain.exception.AllReadyExistException;
import com.epita.filrouge.domain.iphone.IRepositoryIphone;
import com.epita.filrouge.domain.iphone.Iphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import java.time.LocalDate;

@Service
public class AffectationManagementImpl implements IAffectationManagement {

    @Autowired
    private IRepositoryAffectation repositoryAffectation;

    @Autowired
    private IRepositoryCollaborateur repositoryCollaborateur;

    @Autowired
    private IRepositoryIphone repositoryIphone;

    @Override
    public Affectation creerAffectation(String collaborateurUid, String iPhoneNumeroSerie, LocalDate dateAffectation, String numeroLigne, String commentaire) {
        System.out.println("creer affectation--collaborateurUid");
        Collaborateur collaborateur = repositoryCollaborateur.findByUid(collaborateurUid);

        System.out.println("creer affectation--collaborateur.getCollaborateur().getUid----" + collaborateur.getUid());

        System.out.println("application collaborateur.getPrenom() = " + collaborateur.getPrenom());
        System.out.println("application collaborateur.getPrenom() = " + collaborateur.getId());
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

        Iphone iPhone = repositoryIphone.findByNumeroSerie(iPhoneNumeroSerie);
        System.out.println("application iPhone.getIphoneId() = " + iPhone.getIphoneId());
//      test existence Iphone
        Long numeroAffectation = genererNumeroAffectation();
        Affectation affectationACreer = new Affectation(numeroAffectation, dateAffectation, commentaire,collaborateur, iPhone);

        repositoryAffectation.affecter(affectationACreer);

        repositoryCollaborateur.miseAJourCollaborateur(collaborateur, numeroLigne);

        repositoryIphone.miseAJourEtatIphone(iPhoneNumeroSerie);

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
}
