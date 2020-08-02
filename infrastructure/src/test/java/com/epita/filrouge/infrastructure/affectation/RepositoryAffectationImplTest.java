package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.collaborateur.RepositoryCollaborateurImpl;
import com.epita.filrouge.infrastructure.iphone.IphoneEntity;
import com.epita.filrouge.infrastructure.iphone.ModeleIphoneEntity;
import com.epita.filrouge.infrastructure.site.SiteExerciceEntity;
import com.epita.filrouge.infrastructure.uo.UoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Disabled
class RepositoryAffectationImplTest {

    private static final Long SITE_ID = 1L;
    private static final String CODE_SITE = "V2";
    private static final String NOM_SITE = "Valmy2";
    private static final String ADRESSE_POSTALE = "41, Rue de Valmy";
    private static final String CODE_POSTAL = "93100";
    private static final String VILLE = "MONTREUIL";
    private static final String PAYS = "FRANCE";
    private static final LocalDate DATE_CREATION = LocalDate.now();

    private static final Long UO_ID = 1L;
    private static final String CODE_UO = "SDI101";
    private static final String FONCTION_RATTACHEMENT = "BDDF IT";
    private static final String CODE_UO_PARENT = "SDI1";
    private static final String NOM_USAGE_UO = "DATAHUB";
    private static final String NOM_RESPONSABLE_UO = "Alfonse de la Renardiere";

    private static final Long COLLABORATEUR_ID = 1L;
    private static final String COLLABORATEUR_UID = "666999";
    private static final String COLLABORATEUR_NOM = "Doe";
    private static final String COLLABORATEUR_PRENOM = "John";
    private static final String COLLABORATEUR_NUMEROLIGNE = "0612345678";

    private static final Long MODELE_ID = 1L;
    private static final String MODELE_NOMMODELE = "Iphone8";

    private static final Long IPHONE_ID = 1L;
    private static final String IPHONE_NUMEROSERIE = "123456";
    private static final Double IPHONE_PRIX = 800D;
    private static final EtatIphoneEnum IPHONE_ETAT = EtatIphoneEnum.DISPONIBLE;

    private static final Long AFFECTATION_NUMERO = 1L;
    private static final LocalDate AFFECTATION_DATE = LocalDate.now();
    private static final String AFFECTATION_COMMENTAIRE = "Premeire affectation";

    @Autowired
    RepositoryCollaborateurImpl repositoryCollaborateurImpl;

    @Autowired
    IRepositoryAffectation repositoryAffectation;

    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    public void init() {

        SiteExerciceEntity siteExerciceEntity = new SiteExerciceEntity();
        siteExerciceEntity.setCodeSite(CODE_SITE);
        siteExerciceEntity.setNomSite(NOM_SITE);
        siteExerciceEntity.setAdressePostale1(ADRESSE_POSTALE);
        siteExerciceEntity.setCodePostal(CODE_POSTAL);
        siteExerciceEntity.setVille(VILLE);
        siteExerciceEntity.setPays(PAYS);
        siteExerciceEntity.setDateCreation(DATE_CREATION);
        entityManager.persistAndFlush(siteExerciceEntity);

        UoEntity uoEntity = new UoEntity();
        uoEntity.setCodeUo(CODE_UO);
        uoEntity.setFonctionRattachement(FONCTION_RATTACHEMENT);
        uoEntity.setCodeUoParent(CODE_UO_PARENT);
        uoEntity.setNomUsageUo(NOM_USAGE_UO);
        uoEntity.setNomResponsableUo(NOM_RESPONSABLE_UO);
        uoEntity.setSiteExercice(siteExerciceEntity);
        entityManager.persistAndFlush(uoEntity);

        CollaborateurEntity collaborateurEntity = new CollaborateurEntity();
        collaborateurEntity.setUid(COLLABORATEUR_UID);
        collaborateurEntity.setNom(COLLABORATEUR_NOM);
        collaborateurEntity.setPrenom(COLLABORATEUR_PRENOM);
        collaborateurEntity.setNumeroLigne(COLLABORATEUR_NUMEROLIGNE);
        collaborateurEntity.setUo(uoEntity);
        entityManager.persistAndFlush(collaborateurEntity);

        ModeleIphoneEntity modeleIphoneEntity = new ModeleIphoneEntity();
        modeleIphoneEntity.setNomModele(MODELE_NOMMODELE);
        entityManager.persistAndFlush(modeleIphoneEntity);

        IphoneEntity iPhoneEntity = new IphoneEntity();
        iPhoneEntity.setNumeroSerie(IPHONE_NUMEROSERIE);
        iPhoneEntity.setPrixIphone(IPHONE_PRIX);
        iPhoneEntity.setModeleIphoneEntity(modeleIphoneEntity);
        iPhoneEntity.setEtatIphone(IPHONE_ETAT);
        entityManager.persistAndFlush(iPhoneEntity);

//        System.out.println("collaborateurEntity.getCollaborateurId() = " + collaborateurEntity.getCollaborateurId());
//        System.out.println("iPhoneEntity.getIphoneId() = " + iPhoneEntity.getIphoneId());
//        System.out.println("modeleIphoneEntity.getModeleId() = " + modeleIphoneEntity.getModeleId());
    }

    @Test
    @DisplayName("Doit créer un enregistrement Affectation")
    void ShouldCreateAnAffectation() {
//        //Given
        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);

        Uo uo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        uo.setSiteExercice(siteExercice);

        Collaborateur collaborateur = new Collaborateur( COLLABORATEUR_UID, COLLABORATEUR_NOM, COLLABORATEUR_PRENOM, COLLABORATEUR_NUMEROLIGNE,uo);
        collaborateur.setId(1L);

        ModeleIphone modeleIphone = new ModeleIphone(1L, MODELE_NOMMODELE);
        Iphone iphone = new Iphone(1L, IPHONE_NUMEROSERIE, IPHONE_PRIX, modeleIphone, IPHONE_ETAT);

        Affectation affectationACreer = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

////        //When
        repositoryAffectation.affecter(affectationACreer);
////        //Then
        final List<?> affectationRecues = entityManager.getEntityManager()
                .createQuery("select o from AffectationEntity o where o.numeroAffectation = :numeroAffectation")
                .setParameter("numeroAffectation", AFFECTATION_NUMERO)
                .getResultList();
        assertThat(affectationRecues.size()).isEqualTo(1);

        final List<?> IphonesRecus = entityManager.getEntityManager()
                .createQuery("select o from IphoneEntity o")
                .getResultList();
        assertThat(IphonesRecus.size()).isEqualTo(1);

        final List<?> colloaborateursRecus = entityManager.getEntityManager()
                .createQuery("select o from CollaborateurEntity o")
                .getResultList();
        assertThat(colloaborateursRecus.size()).isEqualTo(1);

    }





}