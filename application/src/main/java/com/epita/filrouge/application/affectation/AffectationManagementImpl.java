package com.epita.filrouge.application.affectation;

import com.epita.filrouge.application.collaborateur.CollaborateurManagementImpl;
import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.collaborateur.IRepositoryCollaborateur;
import com.epita.filrouge.domain.iphone.IRepositoryIphone;
import com.epita.filrouge.domain.iphone.Iphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import java.time.LocalDate;
import java.util.Random;

@Service
public class AffectationManagementImpl implements IAffectationManagement {

    @Autowired
    private IRepositoryAffectation repositoryAffectation;

    @Autowired
    private IRepositoryCollaborateur repositoryCollaborateur;

    @Autowired
    private IRepositoryIphone repositoryIphone;

    @Override
    public void save(String collaborateurUiD, String iPhoneNumeroSerie, LocalDate dateAffectation, String numeroLigne, String commentaire) {
        Collaborateur collaborateur = repositoryCollaborateur.findByUid(collaborateurUiD);
//        if (user == null) {
//            throw new NotFoundException(USER_NOT_FOUND, "Email " + userName + " not found");
//        }

        Iphone iPhone = repositoryIphone.findBynumeroSerie(iPhoneNumeroSerie);
//      test existence Iphone
        Long numeroAffectation = genererNumeroAffectation();
        Affectation affectationACreer = new Affectation(numeroAffectation, dateAffectation, commentaire,collaborateur, iPhone);

        repositoryAffectation.save(affectationACreer);

    }

    private static Long genererNumeroAffectation() {
        final Random random = new Random();
        return random.nextLong();
    }
}
