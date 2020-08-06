package com.epita.filrouge.application.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.IRepositoryAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.collaborateur.IRepositoryCollaborateur;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.IRepositoryIphone;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;

import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { AffectationManagementImpl.class } )
public class AffectationManagementImplTest {

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

    private static final LocalDate AFFECTATION_DATE = LocalDate.now();
    private static final String AFFECTATION_COMMENTAIRE = "Premeire affectation";

    @Autowired
    private IAffectationManagement affectationManagementImpl;

    @MockBean
    private IRepositoryCollaborateur repositoryCollaborateur;

    @MockBean
    private IRepositoryIphone repositoryIphone;

    @MockBean
    private IRepositoryAffectation repositoryAffectation;

    @Test
    void ShouldCallFindByUid_And_FindByNumeroSerie_And_repositoryAffectationSave() {
        //Given
        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);
        Uo uo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        uo.setSiteExercice(siteExercice);

        Collaborateur collaborateur = new Collaborateur( COLLABORATEUR_UID, COLLABORATEUR_NOM, COLLABORATEUR_PRENOM, COLLABORATEUR_NUMEROLIGNE,uo);

        ModeleIphone modeleIphone = new ModeleIphone(1L, MODELE_NOMMODELE);
        Iphone iphone = new Iphone(1L, IPHONE_NUMEROSERIE, IPHONE_PRIX, modeleIphone, IPHONE_ETAT);

        when(repositoryCollaborateur.findByUid(COLLABORATEUR_UID)).thenReturn(collaborateur);
        when(repositoryIphone.findByNumeroSerie(IPHONE_NUMEROSERIE)).thenReturn(iphone);

        //When
        affectationManagementImpl.creerAffectation(COLLABORATEUR_UID,IPHONE_NUMEROSERIE,AFFECTATION_DATE,COLLABORATEUR_NUMEROLIGNE,AFFECTATION_COMMENTAIRE);

        //Then
        verify(repositoryCollaborateur, Mockito.times(1)).findByUid(COLLABORATEUR_UID);
        verify(repositoryIphone,Mockito.times(1)).findByNumeroSerie(IPHONE_NUMEROSERIE);
        verify(repositoryAffectation,Mockito.times(1)).affecter(any(Affectation.class));
        verify(repositoryCollaborateur, Mockito.times(1)).miseAJourCollaborateur(collaborateur,COLLABORATEUR_NUMEROLIGNE);
    }
}