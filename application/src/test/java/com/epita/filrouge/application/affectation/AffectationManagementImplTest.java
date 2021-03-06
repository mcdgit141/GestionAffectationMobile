package com.epita.filrouge.application.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.FiltresAffectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.collaborateur.IRepositoryCollaborateur;
import com.epita.filrouge.domain.exception.AllReadyClotureeException;
import com.epita.filrouge.domain.exception.AllReadyExistException;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.IRepositoryIphone;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;

import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AffectationManagementImpl.class})
class AffectationManagementImplTest {

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
    private static final String MODELE_NOMMODELE_CLOTURE = "Iphone9";

    private static final String IPHONE_NUMEROSERIE = "123456";
    private static final Double IPHONE_PRIX = 800D;
    private static final EtatIphoneEnum IPHONE_ETAT = EtatIphoneEnum.DISPONIBLE;

    private static final Long AFFECTATION_NUMERO = 1L;
    private static final LocalDate AFFECTATION_DATE = LocalDate.now();
    private static final String AFFECTATION_COMMENTAIRE = "Premiere affectation";
    private static final String AFFECTATION_MOTIFFIN = "Vole";
    private static final LocalDate AFFECTATION_DATEFIN = LocalDate.now();

    SiteExercice siteExercice = null;
    Uo uo = null;

    private static Collaborateur collaborateur = null;

    ModeleIphone modeleIphone = null;
    private static Iphone iphone = null;
    private static Iphone iphoneAffecte = null;
    private static Affectation affectationEnCours = null;
    private static Affectation affectationAvecDateFin = null;
    private static Affectation affectation = null;

    @Autowired
    private IAffectationManagement affectationManagementImpl;

    @MockBean
    private IRepositoryCollaborateur repositoryCollaborateur;

    @MockBean
    private IRepositoryIphone repositoryIphone;

    @MockBean
    private IRepositoryAffectation repositoryAffectation;

    @BeforeEach
    public void init() {
        SiteExercice siteExercice = new SiteExercice(CODE_SITE, NOM_SITE, ADRESSE_POSTALE, CODE_POSTAL, VILLE, PAYS, DATE_CREATION);
        Uo uo = new Uo(CODE_UO, FONCTION_RATTACHEMENT, CODE_UO_PARENT, NOM_USAGE_UO, NOM_RESPONSABLE_UO);
        uo.setSiteExercice(siteExercice);

        collaborateur = new Collaborateur(COLLABORATEUR_UID, COLLABORATEUR_NOM, COLLABORATEUR_PRENOM, COLLABORATEUR_NUMEROLIGNE, uo);

        ModeleIphone modeleIphone = new ModeleIphone(1L, MODELE_NOMMODELE);
        iphone = new Iphone(1L, IPHONE_NUMEROSERIE, IPHONE_PRIX, modeleIphone, IPHONE_ETAT);
        iphoneAffecte = new Iphone(1L, IPHONE_NUMEROSERIE, IPHONE_PRIX, modeleIphone, EtatIphoneEnum.AFFECTE);

        affectationEnCours = new Affectation(1L, LocalDate.now(), "blabla", collaborateur, iphone);
        affectationAvecDateFin = new Affectation(2L, LocalDate.now(), "blabla", collaborateur, iphone);
        affectationAvecDateFin.setDateFin(LocalDate.now());

    }

    @Test
    void ShouldCallFindByUid_And_FindByNumeroSerie_And_repositoryAffectationSave() {
        //Given

        when(repositoryCollaborateur.findByUid(COLLABORATEUR_UID)).thenReturn(collaborateur);
        when(repositoryIphone.rechercheIphoneParNumeroSerie(IPHONE_NUMEROSERIE)).thenReturn(iphone);

        //When
        affectationManagementImpl.creerAffectation(COLLABORATEUR_UID, IPHONE_NUMEROSERIE, AFFECTATION_DATE, COLLABORATEUR_NUMEROLIGNE, AFFECTATION_COMMENTAIRE);

        //Then
        verify(repositoryCollaborateur, Mockito.times(1)).findByUid(COLLABORATEUR_UID);
        verify(repositoryIphone, Mockito.times(1)).rechercheIphoneParNumeroSerie(IPHONE_NUMEROSERIE);
        verify(repositoryAffectation, Mockito.times(1)).affecter(any(Affectation.class));

    }

    @Test
    void WhenAskToAffectAnIphoneAlreadyAffected_ShouldReturnAnAllReadyExistException() {
        //Given
        when(repositoryCollaborateur.findByUid(COLLABORATEUR_UID)).thenReturn(collaborateur);
        when(repositoryIphone.rechercheIphoneParNumeroSerie(IPHONE_NUMEROSERIE)).thenReturn(iphoneAffecte);

        //When
        Throwable thrown = catchThrowable(() -> affectationManagementImpl.creerAffectation(COLLABORATEUR_UID, IPHONE_NUMEROSERIE, AFFECTATION_DATE, COLLABORATEUR_NUMEROLIGNE, AFFECTATION_COMMENTAIRE));
        String messageAttendu = "Cet iPhone n'est pas disponible, merci de recommencer : " + IPHONE_NUMEROSERIE;
        //Then
        assertThat(thrown).isInstanceOf(AllReadyExistException.class);
        assertThat(thrown).hasMessage(messageAttendu);
    }

    @Test
    void WhenAskToAffectAColloborateurAlreadyAffected_ShouldReturnAnAllReadyExistException() {
        //Given

        when(repositoryCollaborateur.findByUid(COLLABORATEUR_UID)).thenReturn(collaborateur);
        when(repositoryIphone.rechercheIphoneParNumeroSerie(IPHONE_NUMEROSERIE)).thenReturn(iphone);

        List<Affectation> listAffections = new ArrayList();
        listAffections.add(affectationAvecDateFin);
        listAffections.add(affectationEnCours);
        when(repositoryAffectation.rechercheAffectationByUid(COLLABORATEUR_UID)).thenReturn(listAffections);
        //When
        Throwable thrown = catchThrowable(() -> affectationManagementImpl.creerAffectation(COLLABORATEUR_UID, IPHONE_NUMEROSERIE, AFFECTATION_DATE, COLLABORATEUR_NUMEROLIGNE, AFFECTATION_COMMENTAIRE));
        String messageAttendu = "L'affectation pour ce collaborateur existe déjà, merci de la clôturer au préalable : " + COLLABORATEUR_UID;
        //Then
        assertThat(thrown).isInstanceOf(AllReadyExistException.class);
        assertThat(thrown).hasMessage(messageAttendu);
    }

    @Test
    void WhenAskToAffectAColloborateurWithCloseAffectation_ShouldReturnAnAffectation() {
        //Given

        when(repositoryCollaborateur.findByUid(COLLABORATEUR_UID)).thenReturn(collaborateur);
        when(repositoryIphone.rechercheIphoneParNumeroSerie(IPHONE_NUMEROSERIE)).thenReturn(iphone);

        List<Affectation> listAffections = new ArrayList();
        listAffections.add(affectationAvecDateFin);
        when(repositoryAffectation.rechercheAffectationByUid(COLLABORATEUR_UID)).thenReturn(listAffections);
        //When
        affectationManagementImpl.creerAffectation(COLLABORATEUR_UID, IPHONE_NUMEROSERIE, AFFECTATION_DATE, COLLABORATEUR_NUMEROLIGNE, AFFECTATION_COMMENTAIRE);

        //Then
        verify(repositoryCollaborateur, Mockito.times(1)).findByUid(COLLABORATEUR_UID);
        verify(repositoryAffectation, Mockito.times(1)).rechercheAffectationByUid(COLLABORATEUR_UID);
        verify(repositoryIphone, Mockito.times(1)).rechercheIphoneParNumeroSerie(IPHONE_NUMEROSERIE);
        verify(repositoryAffectation, Mockito.times(1)).affecter(any(Affectation.class));

    }

    @Test
    @DisplayName("Cloturer Affectation: Doit vérifier le nombre d'appels à la méthode miseAJourAffectation")
    void When_Cloture_AffectationisCalled_Check_numberOfTimes_ThatMethodsCalled() {
        //Given

        when(repositoryAffectation.chercheAffectationParNumeroAffectation(AFFECTATION_NUMERO)).thenReturn(affectationEnCours);
        Collaborateur collaborateur = affectationEnCours.getCollaborateur();
        Iphone iphone = affectationEnCours.getIphone();

        //When
        affectationManagementImpl.cloturerAffectation(affectationEnCours.getNumeroAffectation(),affectationEnCours.getCommentaire(),AFFECTATION_MOTIFFIN,affectationEnCours.getDateFin());

        //Then
        verify(repositoryAffectation, Mockito.times(1)).miseAjourAffectation(any(Affectation.class));
    }

    @Test
    @DisplayName("Cloturer Affectation: verifier existence du numéro d'affectation")
    void When_Cloture_AffectationisCalled_Check_NumeroAffectation(){
        //given
        when(repositoryAffectation.chercheAffectationParNumeroAffectation(any(Long.class))).thenThrow(new NotFoundException("Message NotfoundExceptionTest"));

        // when + then
        assertThatThrownBy(
                () -> {affectationManagementImpl.cloturerAffectation(3L,
                                        AFFECTATION_COMMENTAIRE,AFFECTATION_MOTIFFIN,AFFECTATION_DATEFIN);}
        ).isInstanceOf(NotFoundException.class);

    }

    @Test
    @DisplayName("Cloturer Affectation: Verifier le résultat de la mise à jour appel de la fonction")
    void When_Cloture_AffectationisCalled_Check_The_Value_After_Update() {
        // given
        affectationEnCours.getIphone().setEtatIphone(EtatIphoneEnum.AFFECTE);
        when(repositoryAffectation.chercheAffectationParNumeroAffectation(AFFECTATION_NUMERO)).thenReturn(affectationEnCours);
        ArgumentCaptor<Affectation> valueCapture = ArgumentCaptor.forClass(Affectation.class);
        //when
        affectationManagementImpl.cloturerAffectation(affectationEnCours.getNumeroAffectation(),affectationEnCours.getCommentaire(),AFFECTATION_MOTIFFIN,affectationEnCours.getDateFin());
        System.out.println("dans les tests application --affectationEnCours.getDateFin() " + affectationEnCours.getDateFin());
        //then
        Mockito.verify(repositoryAffectation).miseAjourAffectation(valueCapture.capture());
        assertAll(
                () -> assertThat(valueCapture.getValue().getCollaborateur().getNumeroLigne()).isNull(),
                () -> assertThat(valueCapture.getValue().getIphone().getEtatIphone()).isEqualTo(EtatIphoneEnum.VOLE),
                () -> assertThat(valueCapture.getValue().getDateFin()).isEqualTo(LocalDate.now())
        );
    }

    @Test
    @DisplayName("Doit transmettre la demande d'affichage avec les bons filtres")
    void ShouldCallWithTheFilters() throws Exception {
        //Given
        FiltresAffectation filtresAffectation = new FiltresAffectation();
        filtresAffectation.setUid(COLLABORATEUR_UID);
        filtresAffectation.setNomModeleIphone(MODELE_NOMMODELE);

        Affectation affectationRetournee = instancierUneAffectation();
        List<Affectation> listeAffectationsRetournee = new ArrayList<>();
        listeAffectationsRetournee.add(affectationRetournee);

        when(repositoryAffectation.rechercheAffectationAvecFiltres(any(FiltresAffectation.class))).thenReturn(listeAffectationsRetournee);

        ArgumentCaptor<FiltresAffectation> filtresAffectationArgumentCaptor = ArgumentCaptor.forClass(FiltresAffectation.class);
        //When
        List<Affectation> listeAffectationRetournee = affectationManagementImpl.listerAffectation(filtresAffectation);

        //Then
        verify(repositoryAffectation).rechercheAffectationAvecFiltres(filtresAffectationArgumentCaptor.capture());

        FiltresAffectation filtresAffectationTransmis = filtresAffectationArgumentCaptor.getValue();

        assertThat(filtresAffectationTransmis)
                .extracting(FiltresAffectation::getUid,
                        FiltresAffectation::getNom,
                        FiltresAffectation::getNumeroLigneCollaborateur,
                        FiltresAffectation::getCodeUo,
                        FiltresAffectation::getNomUsageUo,
                        FiltresAffectation::getNomSite,
                        FiltresAffectation::getNomModeleIphone,
                        FiltresAffectation::getDateRenouvMin,
                        FiltresAffectation::getDateRenouvMax)
                .containsExactly(COLLABORATEUR_UID,
                        null, null, null, null, null,
                        MODELE_NOMMODELE,
                        null, null);

        verify(repositoryAffectation, Mockito.times(1)).rechercheAffectationAvecFiltres(any(FiltresAffectation.class));

    }

    @Test
    @DisplayName("Doit bien renvoyer les affectations quand demande d'affichage avec filtres")
    void ShouldReturnAffectations_WhenFilters() throws Exception {
        //Given
        FiltresAffectation filtresAffectation = new FiltresAffectation();
        filtresAffectation.setUid(COLLABORATEUR_UID);

        Affectation affectationRetournee = instancierUneAffectation();
        List<Affectation> listeAffectationsRetournee = new ArrayList<>();
        listeAffectationsRetournee.add(affectationRetournee);

        when(repositoryAffectation.rechercheAffectationAvecFiltres(any(FiltresAffectation.class))).thenReturn(listeAffectationsRetournee);

        //When
        List<Affectation> listeAffectationRetournee = affectationManagementImpl.listerAffectation(filtresAffectation);

        //Then
        assertThat(listeAffectationRetournee).size().isEqualTo(1);

        assertThat(listeAffectationRetournee.get(0))
                .extracting(
                        Affectation::getNumeroAffectation,
                        Affectation::getDateAffectation,
                        Affectation::getDateRenouvellementPrevue,
                        Affectation::getDateFin, Affectation::getMotifFin)
                .containsExactly(
                        AFFECTATION_NUMERO,
                        AFFECTATION_DATE,
                        AFFECTATION_DATE.plusYears(2),
                        null, null);

    }

    @Test
    @DisplayName("Supprimer Affectation: la méthode supprimerAffectation est appelée une et une seule fois")
    void When_SuppressionAffectationIsCalled_ShouldCalled_MiseAJourAffectationMethod_OneTime() {
        //Given

        when(repositoryAffectation.chercheAffectationParNumeroAffectation(AFFECTATION_NUMERO)).thenReturn(affectationEnCours);

        //When
        affectationManagementImpl.supprimerAffectation(AFFECTATION_NUMERO);

        //Then
        verify(repositoryAffectation, Mockito.times(1)).supprimerAffectation(any(Affectation.class));
    }

    @Test
    @DisplayName("Supprimer Affectation: la méthode chercheAffectationParNumeroAffectation est appelée une et une seule fois")
    void When_SuppressionAffectationIsCalled_ShouldCalled_ChercheAffectationParNumeroAffectationMethod_OneTime() {
        //Given
        when(repositoryAffectation.chercheAffectationParNumeroAffectation(AFFECTATION_NUMERO)).thenReturn(affectationEnCours);

        //When
        affectationManagementImpl.supprimerAffectation(AFFECTATION_NUMERO);

        //Then
        verify(repositoryAffectation, Mockito.times(1)).chercheAffectationParNumeroAffectation(AFFECTATION_NUMERO);
    }

    @Test
    @DisplayName("Supprimer Affectation: throws de NotFoundException pour une affectation inexistante")
    void When_SuppressionAffectationIsCalledForAnUnknowAffectation_ShouldThrowsANotFoundException(){
        //given
        String messageAttendu = "L'affectation avec le numéro suivant n'existe pas ";
        when(repositoryAffectation.chercheAffectationParNumeroAffectation(any(Long.class))).thenThrow(new NotFoundException(messageAttendu));

        //When
        Throwable thrown = catchThrowable(() -> affectationManagementImpl.supprimerAffectation(666L));

        //Then
        assertThat(thrown).isInstanceOf(NotFoundException.class);
        assertThat(thrown).hasMessage(messageAttendu);

    }


    @Test
    @DisplayName("Supprimer Affectation: mise à jour de Collaborateur et Iphone")
    void When_SuppressionAffectationIsCalled_ShouldUpdateCollaborateurAndIphone() {
        // given
        affectationEnCours.getIphone().setEtatIphone(EtatIphoneEnum.AFFECTE);
        when(repositoryAffectation.chercheAffectationParNumeroAffectation(AFFECTATION_NUMERO)).thenReturn(affectationEnCours);

        ArgumentCaptor<Affectation> valueCapture = ArgumentCaptor.forClass(Affectation.class);

        //when
        affectationManagementImpl.supprimerAffectation(AFFECTATION_NUMERO);
        //then
        Mockito.verify(repositoryAffectation).supprimerAffectation(valueCapture.capture());
        assertAll(
                () -> assertThat(valueCapture.getValue().getCollaborateur().getNumeroLigne()).isNull(),
                () -> assertThat(valueCapture.getValue().getIphone().getEtatIphone()).isEqualTo(EtatIphoneEnum.DISPONIBLE)
        );
    }

    private Affectation instancierUneAffectation() {
        LocalDate dateRevouvelementAttentue = AFFECTATION_DATE.plusYears(2);

        SiteExercice siteExercice = new SiteExercice(CODE_SITE, NOM_SITE, ADRESSE_POSTALE, CODE_POSTAL, VILLE, PAYS, DATE_CREATION);
        Uo uo = new Uo(CODE_UO, FONCTION_RATTACHEMENT, CODE_UO_PARENT, NOM_USAGE_UO, NOM_RESPONSABLE_UO);
        uo.setSiteExercice(siteExercice);

        Collaborateur collaborateur = new Collaborateur(COLLABORATEUR_UID, COLLABORATEUR_NOM, COLLABORATEUR_PRENOM, COLLABORATEUR_NUMEROLIGNE, uo);

        ModeleIphone modeleIphone = new ModeleIphone(1L, MODELE_NOMMODELE);
        Iphone iphone = new Iphone(1L, IPHONE_NUMEROSERIE, IPHONE_PRIX, modeleIphone, IPHONE_ETAT);

        return new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

    }
}