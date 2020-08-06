package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntityMapper;
import com.epita.filrouge.infrastructure.collaborateur.RepositoryCollaborateurImpl;
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
//@Disabled
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
    private static final EtatIphoneEnum IPHONE_ETAT = EtatIphoneEnum.DISPONIBLE;

    private static final Long AFFECTATION_NUMERO = 1L;
    private static final LocalDate AFFECTATION_DATE = LocalDate.now();
    private static final String AFFECTATION_COMMENTAIRE = "Premeire affectation";

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
        uoEntity.setSiteExercice(siteExerciceEntity);
        monUoEntityPersiste = entityManager.persistAndFlush(uoEntity);

        CollaborateurEntity collaborateurEntity = new CollaborateurEntity();
        collaborateurEntity.setUid(COLLABORATEUR_UID);
        collaborateurEntity.setNom(COLLABORATEUR_NOM);
        collaborateurEntity.setPrenom(COLLABORATEUR_PRENOM);
        collaborateurEntity.setNumeroLigne(COLLABORATEUR_NUMEROLIGNE);
        collaborateurEntity.setUo(uoEntity);
        monCollaborateurEntityPersiste = entityManager.persistAndFlush(collaborateurEntity);

        ModeleIphoneEntity modeleIphoneEntity = new ModeleIphoneEntity();
        modeleIphoneEntity.setNomModele(MODELE_NOMMODELE);
        monModeleIphoneEntityPersiste = entityManager.persistAndFlush(modeleIphoneEntity);

        IphoneEntity iPhoneEntity = new IphoneEntity();
        iPhoneEntity.setNumeroSerie(IPHONE_NUMEROSERIE);
        iPhoneEntity.setPrixIphone(IPHONE_PRIX);
        iPhoneEntity.setModeleIphoneEntity(modeleIphoneEntity);
        iPhoneEntity.setEtatIphone(IPHONE_ETAT);
        monIphoneEntityPersiste = entityManager.persistAndFlush(iPhoneEntity);

    }

    @Test
    @DisplayName("Doit créer un enregistrement Affectation")
    void ShouldCreateAnAffectation() {
//        //Given
        Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);

        Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);

        Affectation affectationACreer = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE,collaborateur, iphone);

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
    @DisplayName("Doit créer un enregistrement Affectation avec les bonnes valeurs")
    void ShouldCreateAnAffectation_WithTheCorrectValues() {
//        //Given
        Collaborateur collaborateur = collaborateurEntityMapper.mapToDomain(monCollaborateurEntityPersiste);

        Iphone iphone = iphoneEntityMapper.mapToDomain(monIphoneEntityPersiste);

        Affectation affectationACreer = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE,collaborateur, iphone);

////        //When
        repositoryAffectation.affecter(affectationACreer);
////        //Then
        final List<?> affectationRecues = entityManager.getEntityManager()
                .createQuery("select o from AffectationEntity o where o.numeroAffectation = :numeroAffectation")
                .setParameter("numeroAffectation", AFFECTATION_NUMERO)
                .getResultList();
        AffectationEntity affectationEntityGenerated = (AffectationEntity) affectationRecues.get(0);
        CollaborateurEntity collaborateurEntityGenerated = affectationEntityGenerated.getCollaborateur();
//        assertThat(affectationEntityGenerated.getCollaborateur().getNom()).isEqualTo(COLLABORATEUR_NOM);
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
//    @Disabled
    @DisplayName("Recherche Affectation par filtres")
    public void Should_return_Affectations_giving_filters(){
        //giving
        AffectationEntity affectationEntity = new AffectationEntity();
        affectationEntity.setCollaborateur(entityManager.find(CollaborateurEntity.class,entityManager.getId(monCollaborateurEntityPersiste)));
        affectationEntity.setIphone(entityManager.find(IphoneEntity.class,entityManager.getId(monIphoneEntityPersiste)));
        affectationEntity.setDateAffectation(LocalDate.now());
        affectationEntity.setDateRenouvellementPrevue(LocalDate.now().plusYears(2));
        monAffectationEntityPersiste = entityManager.persist(affectationEntity);
        //when

        List<Affectation> result = repositoryAffectation.rechercheAffectationAvecFiltres(monCollaborateurEntityPersiste.getUid(),monCollaborateurEntityPersiste.getNom(),monUoEntityPersiste.getCodeUo(),
                monUoEntityPersiste.getNomUsageUo(),monSiteExercicePersiste.getNomSite(),monCollaborateurEntityPersiste.getNumeroLigne(),monModeleIphoneEntityPersiste.getNomModele(),
                LocalDate.now(), null);

        //then
        assertThat(result.size()).isEqualTo(1);
    }
}