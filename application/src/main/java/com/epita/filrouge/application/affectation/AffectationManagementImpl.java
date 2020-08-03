package com.epita.filrouge.application.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.collaborateur.IRepositoryCollaborateur;
import com.epita.filrouge.domain.iphone.IRepositoryIphone;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.uo.IRepositoryUo;
import com.epita.filrouge.domain.uo.Uo;
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

    @Autowired
    private IRepositoryUo repositoryUo;

    @Override
    public Affectation creerAffectation(String collaborateurUid, String iPhoneNumeroSerie, LocalDate dateAffectation, String numeroLigne, String commentaire) {

        Collaborateur collaborateur = repositoryCollaborateur.findByUid(collaborateurUid);

        System.out.println("application collaborateur.getPrenom() = " + collaborateur.getPrenom());
        System.out.println("application collaborateur.getPrenom() = " + collaborateur.getId());
//        if (user == null) {
//            throw new NotFoundException(USER_NOT_FOUND, "Email " + userName + " not found");
//        }

        // implementation du contrôle de l'existence de cet UID dans la table des affectations car si déjà présent et affectation toujours en cours,
        // on va obliger à clôturer avant de resaisir

        //List<Affectation> affectationDejaCreer = repositoryAffectation.rechercheAffectationByUid(collaborateurUid);


        Iphone iPhone = repositoryIphone.findByNumeroSerie(iPhoneNumeroSerie);
        System.out.println("application iPhone.getIphoneId() = " + iPhone.getIphoneId());
//      test existence Iphone
        Long numeroAffectation = genererNumeroAffectation();
        Affectation affectationACreer = new Affectation(numeroAffectation, dateAffectation, commentaire,collaborateur, iPhone);

        repositoryAffectation.affecter(affectationACreer);

        repositoryCollaborateur.miseAJourCollaborateur(collaborateur, numeroLigne);

        repositoryIphone.miseAJourEtatIphone(iPhone, iPhoneNumeroSerie);

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
//        List<Uo> uoList = repositoryUo.rechercheUoAvecFiltre(codeUo, nomUsageUo);

//        List<Collaborateur> collaborateurList = repositoryCollaborateur.rechercheCollaborateurAvecFiltre(uid, nom, numeroLigneCollaborateur, uoList);



        return repositoryAffectation.rechercheAffectationAvecFiltres(uid,nom,codeUo,nomUsageUo,nomSite,numeroLigneCollaborateur,nomModeleIphone,dateRenouvMin,dateRenouvMax);
    }
}
