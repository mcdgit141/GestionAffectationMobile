package com.epita.filrouge.domain.affectation;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.exception.AllReadyClotureeException;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;


class AffectationTest {
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
    private static final String AFFECTATION_COMMENTAIRE = "Premiere affectation";

    private static final String AFFECTATION_COMMENTAIRE_CLOTURE = "Cloture de l'affectation suite au vol du portable";
    private static final String AFFECTATION_MOTIFFIN = "VOLE";
    private static final LocalDate DATE_FIN =  LocalDate.now();

    private static final String MOTIFFIN_SUPPRIME = "SUPPRIME";

    private static Collaborateur collaborateur = null;
    private static Iphone iphone = null;

    @BeforeAll
    static void initTests() {
        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);
        Uo uo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        uo.setSiteExercice(siteExercice);

        collaborateur = new Collaborateur( COLLABORATEUR_UID, COLLABORATEUR_NOM, COLLABORATEUR_PRENOM, COLLABORATEUR_NUMEROLIGNE,uo);

        ModeleIphone modeleIphone = new ModeleIphone(1L, MODELE_NOMMODELE);
        iphone = new Iphone(1L, IPHONE_NUMEROSERIE, IPHONE_PRIX, modeleIphone, EtatIphoneEnum.AFFECTE);

    }

    @Test
    @DisplayName("Verification règle de la date de renouvellement")
    void ShouldReturn_ADateInTwoYears() {
        //Given
        LocalDate dateRevouvelementAttentue = AFFECTATION_DATE.plusYears(2);


        //When
        Affectation affectationACreer = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);


        //Then
        assertThat(affectationACreer.getDateRenouvellementPrevue()).isEqualTo(dateRevouvelementAttentue);
    }
    @Test
    @DisplayName("Cloturer Affectation: Vérirification que la date de fin, le numéro de ligne du collaborateur et l'état de l'iphone ont bien été changés")
    void Should_Return_Update_DateFin_And_NumeroLigneCollaborateur_And_EtatIphone(){

        // given
        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);
        Uo uo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        uo.setSiteExercice(siteExercice);


        Collaborateur collaborateur = new Collaborateur( COLLABORATEUR_UID, COLLABORATEUR_NOM, COLLABORATEUR_PRENOM, COLLABORATEUR_NUMEROLIGNE,uo);
        ModeleIphone modeleIphone = new ModeleIphone(1L, MODELE_NOMMODELE);
        Iphone iphone = new Iphone(1L, IPHONE_NUMEROSERIE, IPHONE_PRIX, modeleIphone, EtatIphoneEnum.AFFECTE);
        Affectation affectation = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

        // when
        Affectation affectationACloturerFinal = affectation.reglesAppliqueesPourCloturerAffectation(collaborateur,
                iphone, AFFECTATION_COMMENTAIRE_CLOTURE, AFFECTATION_MOTIFFIN, DATE_FIN);

        //then
        assertThat(affectationACloturerFinal.getDateFin()).isEqualTo(LocalDate.now());
        assertThat(affectationACloturerFinal.getCollaborateur().getNumeroLigne()).isNull();
        assertThat(affectationACloturerFinal.getIphone().getEtatIphone()).isEqualTo(EtatIphoneEnum.VOLE);
    }
    @Test
    @DisplayName("Lors suppression d'une affectation, doit renvoyer une Affectation avec iphone et Collaborateur mis à jour")
    void ShouldReturnAnAffectionWithIphoneAndCollabrateurUpdated_WhenAskToDeleteAnAffectation() {
        //Given

        Affectation affectation = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

        //When
        affectation.reglesAppliqueesPourSuppressionAffectation();

        //Then
        assertThat(affectation.getCollaborateur().getNumeroLigne()).isNull();
        assertThat(affectation.getIphone().getEtatIphone()).isEqualTo(EtatIphoneEnum.DISPONIBLE);
    }

    @Test
    @DisplayName("Demande de suppression d'une affection non en cours provoque une erreur")
    void ShouldReturnAnError_WhenAskToDeleteAnInactiveAffectation(){
        //Given
        Affectation affectation = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);
        affectation.setDateFin(LocalDate.now());

        String messageAttendu = "Cette affectation a une date de fin renseignée. Elle ne peut donc être supprimée.";

        //When
        Throwable thrown = catchThrowable(() -> affectation.reglesAppliqueesPourSuppressionAffectation());

        //Then
        assertThat(thrown).isInstanceOf(AllReadyClotureeException.class);
        assertThat(thrown).hasMessage(messageAttendu);

    }

    @Test
    @DisplayName("Demande de suppression d'une affection ayant une date d'affectation antérieur à aujourd'hui")
    void ShouldReturnAnError_WhenAskToDeleteAnAffectationWithAnAffectationDateOlderThanToday(){
        //Given
        Affectation affectation = new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE.minusDays(1), AFFECTATION_COMMENTAIRE, collaborateur, iphone);

        String messageAttendu = "Cette affectation a une date d'affectation anterieure à aujourd'hui. Elle ne peut donc être supprimée.";

        //When
        Throwable thrown = catchThrowable(() -> affectation.reglesAppliqueesPourSuppressionAffectation());

        //Then
        assertThat(thrown).isInstanceOf(AllReadyClotureeException.class);
        assertThat(thrown).hasMessage(messageAttendu);

    }
}
