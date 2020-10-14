package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.FiltresAffectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.utilisateur.Utilisateur;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntityMapper;
import com.epita.filrouge.infrastructure.iphone.IphoneEntity;
import com.epita.filrouge.infrastructure.iphone.IphoneEntityMapper;
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
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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
    private static final Long AFFECTATION_NUMERO_NONTROUVE= 2L;
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

        monCollaborateurEntityPersiste = persisteCollaborateur(COLLABORATEUR_UID,COLLABORATEUR_NOM,COLLABORATEUR_PRENOM,
                COLLABORATEUR_NUMEROLIGNE, monUoEntityPersiste);
//        monCollaborateurEntityPersiste = entityManager.persistAndFlush(collaborateurEntity);

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
    @DisplayName("Cloture Affectation: Une demande de clôture doit mettre à null le numéro de ligne du collaborateur")
    void ShouldUpdate_NumeroLigne_Collaborateur_When_AskToClose_AnAffectation() {
        //Given
        persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);

        Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);
        collaborateur.setNumeroLigne(null);

        Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);

        Affectation affectationACloturer = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

        //When
        repositoryAffectation.miseAjourAffectation(affectationACloturer);

        //Then
        final CollaborateurEntity collaborateurEnTable = (CollaborateurEntity) entityManager.getEntityManager()
                .createQuery("select o from CollaborateurEntity o where o.uid = :uid")
                .setParameter("uid", COLLABORATEUR_UID)
                .getSingleResult();

        assertThat(collaborateurEnTable.getNumeroLigne()).isNull();
    }

    @Test
    @DisplayName("Cloture Affectation: Une demande de clôture doit mettre à jour l'état de l'iphone à DISPONIBLE")
    void ShouldUpdate_EtatIphone_Disponible_When_AskToClose_AnAffectation() {
        //Given
        persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);

        Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);

        Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);
        iphone.setEtatIphone(EtatIphoneEnum.DISPONIBLE);

        Affectation affectationACloturer= new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

        //When
        repositoryAffectation.miseAjourAffectation(affectationACloturer);

        //Then
        final IphoneEntity iPhoneEnTable = (IphoneEntity) entityManager.getEntityManager()
                .createQuery("select o from IphoneEntity o where o.numeroSerie = :numeroSerie")
                .setParameter("numeroSerie", IPHONE_NUMEROSERIE)
                .getSingleResult();

        assertThat(iPhoneEnTable.getEtatIphone()).isEqualTo(EtatIphoneEnum.DISPONIBLE);
    }
    @Test
    @DisplayName("Cloture Affectation: Une demande de clôture doit mettre à jour la date de fin")
    void ShouldUpdate_DateFin_When_AskToClose_AnAffectation() {
        //Given
       persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);

        Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);

        Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);

        Affectation affectationACloturer= new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);
        affectationACloturer.setDateFin(LocalDate.now());
        
        //When
        repositoryAffectation.miseAjourAffectation(affectationACloturer);

        //Then
        final AffectationEntity affectationEnTable = (AffectationEntity) entityManager.getEntityManager()
                .createQuery("select o from AffectationEntity o where o.numeroAffectation = :numeroAffectation")
                .setParameter("numeroAffectation", AFFECTATION_NUMERO)
                .getSingleResult();

        assertThat(affectationEnTable.getDateFin()).isEqualTo(LocalDate.now());
    }
    @Test
    @DisplayName("Cloture Affectation: NotFoundException si l'affectation à cloturer n'est pas trouvée")
    void CloseAffectation_Should_Throw_Exception_If_Affectation_Do_Not_Exist(){
        //giving BeforeEach
        persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);

        Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);

        Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);

        Affectation affectationACloturer= new Affectation(AFFECTATION_NUMERO_NONTROUVE, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

        //when + then
        assertThatThrownBy(
                () -> {repositoryAffectation.miseAjourAffectation(affectationACloturer);}
        ).isInstanceOf(NotFoundException.class);
    }
    @Test
    @DisplayName("Recherche Affectation par le numero d'affectation doit trouver")
    void Should_Return_Affectation_Giving_NumeroAffectation() {
        //giving
        persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);

        Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);

        Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);

        Affectation affectation = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

        //when

        Affectation affectationResult = repositoryAffectation.chercheAffectationParNumeroAffectation(AFFECTATION_NUMERO);

        //then
        assertThat(affectationResult).isNotNull();
        assertThat(affectationResult).extracting(
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
    @DisplayName("Recherche Affectation par le numero d'affectation renvoie un NotFoundException si le numero d'affectation n'existe pas")
    void Should_Return_NotFoundException_When_NumeroAffectation_DontExist() {
        //giving
        persisteAffectation(monCollaborateurEntityPersiste, monIphoneEntityPersiste,
                AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, AFFECTATION_NUMERO);

        Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);

        Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);

        Affectation affectation = new Affectation(AFFECTATION_NUMERO_NONTROUVE, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

        //when

        Affectation affectationNonTrouvee = repositoryAffectation.chercheAffectationParNumeroAffectation(AFFECTATION_NUMERO);

        //then
        assertThatThrownBy(
                () -> {repositoryAffectation.chercheAffectationParNumeroAffectation(AFFECTATION_NUMERO_NONTROUVE);}
        ).isInstanceOf(NotFoundException.class);
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
        filtresAffectation.setCritereDeTri("UID");
        filtresAffectation.setSensduTri("D");

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

    @Test
    @DisplayName("rechercheAffectationAvecFiltres :  taille par défaut de la page fixé à 10")
    void rechercheAffectationAvecFiltres_should_return_10_rows_when_no_pageSize_given(){
        //given
        enrichirDbDeTest();
        FiltresAffectation monFiltre = new FiltresAffectation();

        //when
        List<Affectation> result = repositoryAffectation.rechercheAffectationAvecFiltres(monFiltre);

        //then
        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("rechercheAffectationAvecFiltre : restitution d'un nombre de ligne correspondant à la taille de la page")
    void rechercheAffectationAvecFiltre_should_return_rows_corresponding_to_the_pageSize(){
        //given
        enrichirDbDeTest();
        FiltresAffectation monFiltre = new FiltresAffectation();
        monFiltre.setTaillePage(5);

        //when
        List<Affectation> result = repositoryAffectation.rechercheAffectationAvecFiltres(monFiltre);

        //then
        assertThat(result.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("rechercheAffectationAvecFiltres : respects des critères de tri fournis")
    void rechercheAffectationAvecFiltres_should_sort_results_accorging_to_given_criterias(){
        //given
        enrichirDbDeTest();
        FiltresAffectation monFiltre = new FiltresAffectation();
        monFiltre.setTaillePage(5);
        monFiltre.setCritereDeTri("UID");
        monFiltre.setSensduTri("D");

        //when
        List<Affectation> result = repositoryAffectation.rechercheAffectationAvecFiltres(monFiltre);

        //then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(5),
                () -> assertThat(result.get(0).getCollaborateur().getUid()).isEqualTo("AAAAAA"),
                () -> assertThat(result.get(1).getCollaborateur().getUid()).isEqualTo("999999"),
                () -> assertThat(result.get(2).getCollaborateur().getUid()).isEqualTo("888888"),
                () -> assertThat(result.get(3).getCollaborateur().getUid()).isEqualTo("777777"),
                () -> assertThat(result.get(4).getCollaborateur().getUid()).isEqualTo("666666")
        );
    }

    @Test
    @DisplayName("rechercheAffectationAvecFiltre : tri ascendant par defaut")
    void rechercheAffectationAvecFiltres_should_sort_ascending_by_default(){
        //given
        enrichirDbDeTest();
        FiltresAffectation monFiltre = new FiltresAffectation();
        monFiltre.setTaillePage(5);
        monFiltre.setCritereDeTri("UID");

        //when
        List<Affectation> result = repositoryAffectation.rechercheAffectationAvecFiltres(monFiltre);

        //then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(5),
                () -> assertThat(result.get(0).getCollaborateur().getUid()).isEqualTo("000000"),
                () -> assertThat(result.get(1).getCollaborateur().getUid()).isEqualTo("111111"),
                () -> assertThat(result.get(2).getCollaborateur().getUid()).isEqualTo("222222"),
                () -> assertThat(result.get(3).getCollaborateur().getUid()).isEqualTo("333333"),
                () -> assertThat(result.get(4).getCollaborateur().getUid()).isEqualTo("444444")
        );
    }

    @Test
    @DisplayName("rechercheAffectationAvecFiltres : restitution de la première page, si aucune page spécifique demandé")
    void rechercheAffectationAvecFiltres_should_return_first_page_if_none_asked(){
        //given
        enrichirDbDeTest();
        FiltresAffectation monFiltre = new FiltresAffectation();
        monFiltre.setTaillePage(5);
        monFiltre.setCritereDeTri("UID");

        //when
        List<Affectation> result = repositoryAffectation.rechercheAffectationAvecFiltres(monFiltre);

        //then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(5),
                () -> assertThat(result.get(0).getCollaborateur().getUid()).isEqualTo("000000"),
                () ->assertThat(entityManager.getEntityManager().createQuery("select a from AffectationEntity a where a.collaborateur.uid < '000000'")
                .getResultList().size()
                                ).isZero()
        );
    }

    @Test
    @DisplayName("rechercheAffectationAvecFiltre : restitution de la page demandée")
    void rechercheAffectationAvecFiltre_should_return_the_page_asked(){
        //given
        enrichirDbDeTest();
        FiltresAffectation monFiltre = new FiltresAffectation();
        monFiltre.setTaillePage(5);
        monFiltre.setCritereDeTri("UID");
        monFiltre.setNumeroDePage(2);

        //when
        List<Affectation> result = repositoryAffectation.rechercheAffectationAvecFiltres(monFiltre);

        //then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(5),
                () -> assertThat(result.get(0).getCollaborateur().getUid()).isEqualTo("555555"),
                () ->assertThat(entityManager.getEntityManager().createQuery("select a from AffectationEntity a where a.collaborateur.uid < '555555'")
                        .getResultList().size()
                ).isEqualTo(5)
        );
    }

    @Test
    @DisplayName("rechercheAffectationSansFiltre : restitution du nombre d'enregistrement total")
    void rechercheAffectationAvecFiltre_retourne_dix(){
        //given
        enrichirDbDeTest();
        FiltresAffectation monFiltre = new FiltresAffectation();


        //when
        AbstractMap.Entry<List<Integer>, List<Affectation>> result = repositoryAffectation.rechercheAffectationAvecFiltres2(monFiltre);
        List<Integer> meta = result.getKey();
        List<Affectation> affectationList = result.getValue();

        //then
        assertThat(meta.get(0)).isEqualTo(11);  // nombre enregt total
        assertThat(meta.get(1)).isEqualTo(2);   // nombre de pages
        assertThat(meta.get(2)).isEqualTo(1);   // page retournée
        assertThat(meta.get(3)).isEqualTo(10);   // nombre d'enregt par page
        assertThat(affectationList.size()).isEqualTo(10);

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
        monAffectationEntityPersiste = entityManager.persistAndFlush(affectationEntity);
    }

    private CollaborateurEntity persisteCollaborateur(String uid, String nom, String prenom, String numeroLine,UoEntity uoEntity){
        CollaborateurEntity monCollaborateurEntity = new CollaborateurEntity();
        monCollaborateurEntity.setUid(uid);
        monCollaborateurEntity.setNom(nom);
        monCollaborateurEntity.setPrenom(prenom);
        monCollaborateurEntity.setNumeroLigne(numeroLine);
        monCollaborateurEntity.setUo(uoEntity);

        return entityManager.persistAndFlush(monCollaborateurEntity);
    }

    private void enrichirDbDeTest(){
        CollaborateurEntity C1 = persisteCollaborateur("111111","nom1", "prenom1","1111111111",monUoEntityPersiste);
        CollaborateurEntity C2 = persisteCollaborateur("222222","nom2", "prenom2","2222222222",monUoEntityPersiste);
        CollaborateurEntity C3 = persisteCollaborateur("333333","nom3", "prenom3","3333333333",monUoEntityPersiste);
        CollaborateurEntity C4 = persisteCollaborateur("444444","nom4", "prenom4","4444444444",monUoEntityPersiste);
        CollaborateurEntity C5 = persisteCollaborateur("555555","nom5", "prenom5","5555555555",monUoEntityPersiste);
        CollaborateurEntity C6 = persisteCollaborateur("666666","nom6", "prenom6","6666666666",monUoEntityPersiste);
        CollaborateurEntity C7 = persisteCollaborateur("777777","nom7", "prenom7","7777777777",monUoEntityPersiste);
        CollaborateurEntity C8 = persisteCollaborateur("888888","nom8", "prenom8","8888888888",monUoEntityPersiste);
        CollaborateurEntity C9 = persisteCollaborateur("999999","nom9", "prenom9","9999999999",monUoEntityPersiste);
        CollaborateurEntity C10 = persisteCollaborateur("000000","nom10", "prenom10","0000000000",monUoEntityPersiste);
        CollaborateurEntity C11 = persisteCollaborateur("AAAAAA","nomA", "prenomA","AAAAAAAAAA",monUoEntityPersiste);
        IphoneEntity I1 = persisteIphone("1212121212",1001.00,monModeleIphoneEntityPersiste,EtatIphoneEnum.DISPONIBLE);
        IphoneEntity I2 = persisteIphone("2323232323",1001.00,monModeleIphoneEntityPersiste,EtatIphoneEnum.DISPONIBLE);
        IphoneEntity I3 = persisteIphone("3434343434",1001.00,monModeleIphoneEntityPersiste,EtatIphoneEnum.DISPONIBLE);
        IphoneEntity I4 = persisteIphone("4545454545",1001.00,monModeleIphoneEntityPersiste,EtatIphoneEnum.DISPONIBLE);
        IphoneEntity I5 = persisteIphone("5656565656",1001.00,monModeleIphoneEntityPersiste,EtatIphoneEnum.DISPONIBLE);
        IphoneEntity I6 = persisteIphone("6767676767",1001.00,monModeleIphoneEntityPersiste,EtatIphoneEnum.DISPONIBLE);
        IphoneEntity I7 = persisteIphone("7878787878",1001.00,monModeleIphoneEntityPersiste,EtatIphoneEnum.DISPONIBLE);
        IphoneEntity I8 = persisteIphone("8989898989",1001.00,monModeleIphoneEntityPersiste,EtatIphoneEnum.DISPONIBLE);
        IphoneEntity I9 = persisteIphone("9090909090",1001.00,monModeleIphoneEntityPersiste,EtatIphoneEnum.DISPONIBLE);
        IphoneEntity I10 =  persisteIphone("1919191919",1001.00,monModeleIphoneEntityPersiste,EtatIphoneEnum.DISPONIBLE);
        IphoneEntity I11 = persisteIphone("1818181818",1001.00,monModeleIphoneEntityPersiste,EtatIphoneEnum.DISPONIBLE);

        persisteAffectation(C1,I1,LocalDate.now(),"commentaire 1",1L);
        persisteAffectation(C2,I2,LocalDate.now(),"commentaire 2",2L);
        persisteAffectation(C3,I3,LocalDate.now(),"commentaire 3",3L);
        persisteAffectation(C4,I4,LocalDate.now(),"commentaire 4",4L);
        persisteAffectation(C5,I5,LocalDate.now(),"commentaire 5",5L);
        persisteAffectation(C6,I6,LocalDate.now(),"commentaire 6",6L);
        persisteAffectation(C7,I7,LocalDate.now(),"commentaire 7",7L);
        persisteAffectation(C8,I8,LocalDate.now(),"commentaire 8",8L);
        persisteAffectation(C9,I9,LocalDate.now(),"commentaire 9",9L);
        persisteAffectation(C10,I10,LocalDate.now(),"commentaire 10",10L);
        persisteAffectation(C11,I11,LocalDate.now(),"commentaire 11",11L);
    }
}