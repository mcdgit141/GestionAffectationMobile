package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.FiltresAffectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntityMapper;
import com.epita.filrouge.infrastructure.iphone.IphoneEntity;
import com.epita.filrouge.infrastructure.iphone.IphoneEntityMapper;
import com.epita.filrouge.infrastructure.iphone.ModeleIphoneEntity;
import com.epita.filrouge.infrastructure.site.SiteExerciceEntity;
import com.epita.filrouge.infrastructure.uo.UoEntity;
import org.junit.jupiter.api.BeforeEach;
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
class RepositoryAffectationImplTest {

    private static final String CODE_SITE = "V2";
    private static final String NOM_SITE = "Valmy2";
    private static final String ADRESSE_POSTALE = "41, Rue de Valmy";
    private static final String CODE_POSTAL = "93100";
    private static final String VILLE = "MONTREUIL";
    private static final String PAYS = "FRANCE";
    private static final LocalDate DATE_CREATION = LocalDate.now();

    private static final String CODE_UO = "SDI101";
    private static final String FONCTION_RATTACHEMENT = "BDDF IT";
    private static final String CODE_UO_PARENT = "SDI1";
    private static final String NOM_USAGE_UO = "DATAHUB";
    private static final String NOM_RESPONSABLE_UO = "Alfonse de la Renardiere";

    private static final String COLLABORATEUR_UID = "666999";
    private static final String COLLABORATEUR_NOM = "Doe";
    private static final String COLLABORATEUR_PRENOM = "John";
    private static final String COLLABORATEUR_NUMEROLIGNE = "0612345678";

    private static final String MODELE_NOMMODELE = "Iphone8";

    private static final String IPHONE_NUMEROSERIE = "123456";
    private static final Double IPHONE_PRIX = 800D;
    private static final EtatIphoneEnum IPHONE_ETAT = EtatIphoneEnum.AFFECTE;

    private static final Long AFFECTATION_NUMERO = 1L;
    private static final LocalDate AFFECTATION_DATE = LocalDate.now();
    private static final String AFFECTATION_COMMENTAIRE = "Premiere affectation";

    private SiteExerciceEntity monSiteExercicePersiste;
    private UoEntity monUoEntityPersiste;
    private CollaborateurEntity monCollaborateurEntityPersiste;
    private ModeleIphoneEntity monModeleIphoneEntityPersiste;
    private IphoneEntity monIphoneEntityPersiste;
    private AffectationEntity monAffectationEntityPersiste;


    @Autowired
    IRepositoryAffectation repositoryAffectation;

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CollaborateurEntityMapper collaborateurEntityMapper;

    @Autowired
    IphoneEntityMapper iphoneEntityMapper;

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
        monSiteExercicePersiste = entityManager.persistAndFlush(siteExerciceEntity);

        UoEntity uoEntity = new UoEntity();
        uoEntity.setCodeUo(CODE_UO);
        uoEntity.setFonctionRattachement(FONCTION_RATTACHEMENT);
        uoEntity.setCodeUoParent(CODE_UO_PARENT);
        uoEntity.setNomUsageUo(NOM_USAGE_UO);
        uoEntity.setNomResponsableUo(NOM_RESPONSABLE_UO);
        uoEntity.setSiteExercice(monSiteExercicePersiste);
        monUoEntityPersiste = entityManager.persistAndFlush(uoEntity);

        CollaborateurEntity collaborateurEntity = new CollaborateurEntity();
        collaborateurEntity.setUid(COLLABORATEUR_UID);
        collaborateurEntity.setNom(COLLABORATEUR_NOM);
        collaborateurEntity.setPrenom(COLLABORATEUR_PRENOM);
        collaborateurEntity.setNumeroLigne(COLLABORATEUR_NUMEROLIGNE);
        collaborateurEntity.setUo(monUoEntityPersiste);
        monCollaborateurEntityPersiste = entityManager.persistAndFlush(collaborateurEntity);

        ModeleIphoneEntity modeleIphoneEntity = new ModeleIphoneEntity();
        modeleIphoneEntity.setNomModele(MODELE_NOMMODELE);
        monModeleIphoneEntityPersiste = entityManager.persistAndFlush(modeleIphoneEntity);

        monIphoneEntityPersiste = persisteIphone(IPHONE_NUMEROSERIE, IPHONE_PRIX, modeleIphoneEntity, IPHONE_ETAT);

    }

    @Test
    @DisplayName("Doit créer un enregistrement Affectation")
    void ShouldCreateAnAffectation() {
//        //Given
        Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);

        Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);

        Affectation affectationACreer = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

////        //When
        repositoryAffectation.affecter(affectationACreer);
////        //Then
        final List<?> affectationRecues = entityManager.getEntityManager()
                .createQuery("select o from AffectationEntity o where o.numeroAffectation = :numeroAffectation")
                .setParameter("numeroAffectation", AFFECTATION_NUMERO)
                .getResultList();
        assertThat(affectationRecues.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Une demande de suppression doit supprimer une affectation")
    void ShouldDeleteAnAffectation_WhenAskToDeleteAnAffectation() {
        //Given
        persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);

        Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);

        Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);

        Affectation affectationASupprimer = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);


        //When
        repositoryAffectation.supprimerAffectation(affectationASupprimer);

        //Then
        final List<?> affectationRecues = entityManager.getEntityManager()
                .createQuery("select o from AffectationEntity o where o.numeroAffectation = :numeroAffectation")
                .setParameter("numeroAffectation", AFFECTATION_NUMERO)
                .getResultList();
        assertThat(affectationRecues.size()).isZero();
    }

@Test
    @DisplayName("Une demande de suppression doit mettre à jour le collaborateur")
    void ShouldUpdateACollaborateur_WhenAskToDeleteAnAffectation() {
        //Given
        persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);

        Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);
        collaborateur.setNumeroLigne(null);

        Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);

        Affectation affectationASupprimer = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);


        //When
        repositoryAffectation.supprimerAffectation(affectationASupprimer);

        //Then
        final CollaborateurEntity collaborateurEnTable = (CollaborateurEntity) entityManager.getEntityManager()
                .createQuery("select o from CollaborateurEntity o where o.uid = :uid")
                .setParameter("uid", COLLABORATEUR_UID)
                .getSingleResult();

        assertThat(collaborateurEnTable.getNumeroLigne()).isNull();
    }


@Test
    @DisplayName("Une demande de suppression doit mettre à jour l'iphone")
    void ShouldUpdateIphone_WhenAskToDeleteAnAffectation() {
    //Given
    persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);

    Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);

    Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);
    iphone.setEtatIphone(EtatIphoneEnum.DISPONIBLE);

    Affectation affectationASupprimer = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

    //When
    repositoryAffectation.supprimerAffectation(affectationASupprimer);

    //Then
    final IphoneEntity iPhoneEnTable = (IphoneEntity) entityManager.getEntityManager()
                .createQuery("select o from IphoneEntity o where o.numeroSerie = :numeroSerie")
                .setParameter("numeroSerie", IPHONE_NUMEROSERIE)
                .getSingleResult();

        assertThat(iPhoneEnTable.getEtatIphone()).isEqualTo(EtatIphoneEnum.DISPONIBLE);
    }

    @Test
    @DisplayName("Doit créer un enregistrement Affectation avec les bonnes valeurs")
    void ShouldCreateAnAffectation_WithTheCorrectValues() {
//        //Given
        Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);

        Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);

        Affectation affectationACreer = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

////        //When
        repositoryAffectation.affecter(affectationACreer);
////        //Then
        final List<?> affectationRecues = entityManager.getEntityManager()
                .createQuery("select o from AffectationEntity o where o.numeroAffectation = :numeroAffectation")
                .setParameter("numeroAffectation", AFFECTATION_NUMERO)
                .getResultList();
        AffectationEntity affectationEntityGenerated = (AffectationEntity) affectationRecues.get(0);
        CollaborateurEntity collaborateurEntityGenerated = affectationEntityGenerated.getCollaborateur();
        assertThat(affectationEntityGenerated)
                .extracting(AffectationEntity::getNumeroAffectation,
                        AffectationEntity::getDateAffectation,
                        AffectationEntity::getCommentaire
                )
                .containsExactly(AFFECTATION_NUMERO,
                        AFFECTATION_DATE,
                        AFFECTATION_COMMENTAIRE
                );
        assertThat(collaborateurEntityGenerated)
                .extracting(CollaborateurEntity::getUid,
                        CollaborateurEntity::getNom,
                        CollaborateurEntity::getPrenom,
                        CollaborateurEntity::getNumeroLigne
                )
                .containsExactly(COLLABORATEUR_UID,
                        COLLABORATEUR_NOM,
                        COLLABORATEUR_PRENOM,
                        COLLABORATEUR_NUMEROLIGNE
                );
    }

    @Test
    @DisplayName("Aucune Affectation pour une recherche Affectation par filtres")
    void Should_return_NoAffectation_giving_AnUnusedSiteExerciste() {
        //giving
        persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);

        //when

        FiltresAffectation filtresAffectation = new FiltresAffectation();
        filtresAffectation.setNomSite("Pompei");

        List<Affectation> result = repositoryAffectation.rechercheAffectationAvecFiltres(filtresAffectation);

        //then
        assertThat(result.size()).isZero();
    }

    @Test
    @DisplayName("Recherche Affectation sur filtre uid")
    void Should_return_Affectations_giving_filterUid() {
        //giving
        persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);


        FiltresAffectation filtresAffectation = new FiltresAffectation();
        filtresAffectation.setUid(COLLABORATEUR_UID);

        //when

        List<Affectation> result = repositoryAffectation.rechercheAffectationAvecFiltres(filtresAffectation);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).extracting(
                Affectation::getNumeroAffectation,
                Affectation::getDateAffectation,
                Affectation::getDateRenouvellementPrevue,
                Affectation::getDateFin, Affectation::getMotifFin,
                Affectation::getCommentaire)
                .containsExactly(
                        AFFECTATION_NUMERO,
                        AFFECTATION_DATE,
                        AFFECTATION_DATE.plusYears(2),
                        null, null,
                        AFFECTATION_COMMENTAIRE
                        );

    }

    @Test
    @DisplayName("Recherche Affectation sur tous les filtres")
    void Should_return_Affectation_giving_OnlyOneFilter() {
        //giving
        persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);

        FiltresAffectation filtresAffectation = new FiltresAffectation();
        filtresAffectation.setUid(COLLABORATEUR_UID);
        filtresAffectation.setNom(COLLABORATEUR_NOM);
        filtresAffectation.setNumeroLigneCollaborateur(COLLABORATEUR_NUMEROLIGNE);
        filtresAffectation.setCodeUo(CODE_UO);
        filtresAffectation.setNomUsageUo(NOM_USAGE_UO);
        filtresAffectation.setNomSite(NOM_SITE);
        filtresAffectation.setNomModeleIphone(MODELE_NOMMODELE);
        filtresAffectation.setDateRenouvMin(AFFECTATION_DATE);
        filtresAffectation.setDateRenouvMax(AFFECTATION_DATE.plusYears(5));
        //when

        List<Affectation> result = repositoryAffectation.rechercheAffectationAvecFiltres(filtresAffectation);

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Aucune Affectation : en dehors plage date de renouvellement")
    void Should_return_NoAffectation_giving_DateRenouvellementIsOutOfBound() {
        //giving
        persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);

        FiltresAffectation filtresAffectation = new FiltresAffectation();
        filtresAffectation.setDateRenouvMin(AFFECTATION_DATE);
        filtresAffectation.setDateRenouvMax(AFFECTATION_DATE.plusYears(1));
        //when

        List<Affectation> result = repositoryAffectation.rechercheAffectationAvecFiltres(filtresAffectation);

        //then
        assertThat(result.size()).isZero();
    }

    @Test
    @DisplayName("Recherche des affectations d'un collaborateur")
    void ShouldReturnListOfAffection_WhenColloborateurHaveManyAffections() {
        //Given


        IphoneEntity monIphoneCasseEntityPersiste = persisteIphone("111222333", 800D,
                monModeleIphoneEntityPersiste, EtatIphoneEnum.CASSE);

        persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);
        persisteAffectation(monCollaborateurEntityPersiste, monIphoneCasseEntityPersiste,
                LocalDate.now().plusYears(-1), "tombé par terre", 421L);


        //When
        List<Affectation> listeAffectationsRetournees = repositoryAffectation.rechercheAffectationByUid(COLLABORATEUR_UID);

        //Then
        assertThat(listeAffectationsRetournees.size()).isEqualTo(2);
    }

    private IphoneEntity persisteIphone(String numeroSerie, Double iphonePrix,
                                        ModeleIphoneEntity modeleIphoneEntity, EtatIphoneEnum iphoneEtat) {
        IphoneEntity iPhoneEntity = new IphoneEntity();
        iPhoneEntity.setNumeroSerie(numeroSerie);
        iPhoneEntity.setPrixIphone(iphonePrix);
        iPhoneEntity.setModeleIphoneEntity(modeleIphoneEntity);
        iPhoneEntity.setEtatIphone(iphoneEtat);
        return entityManager.persistAndFlush(iPhoneEntity);
    }

    private void persisteAffectation(CollaborateurEntity collaborateurEntity, IphoneEntity iphoneEntity,
                                     LocalDate dateAffectation, String commentaire, Long numeroAffectation) {
        AffectationEntity affectationEntity = new AffectationEntity();
        affectationEntity.setCollaborateur(entityManager.find(CollaborateurEntity.class, entityManager.getId(collaborateurEntity)));
        affectationEntity.setIphone(entityManager.find(IphoneEntity.class, entityManager.getId(iphoneEntity)));
        affectationEntity.setDateAffectation(dateAffectation);
        affectationEntity.setDateRenouvellementPrevue(LocalDate.now().plusYears(2));
        affectationEntity.setCommentaire(commentaire);
        affectationEntity.setNumeroAffectation(numeroAffectation);
        monAffectationEntityPersiste = entityManager.persist(affectationEntity);
    }
}