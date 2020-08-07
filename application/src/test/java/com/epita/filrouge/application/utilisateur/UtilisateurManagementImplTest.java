package com.epita.filrouge.application.utilisateur;


import com.epita.filrouge.application.collaborateur.ICollaborateurManagement;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.exception.BadRequestException;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.domain.utilisateur.IRepositoryUtilisateur;
import com.epita.filrouge.domain.utilisateur.Utilisateur;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UtilisateurManagementImpl.class } )
public class UtilisateurManagementImplTest {

    @MockBean
    private ICollaborateurManagement collaborateurManagement;

    @Autowired
    private IUtilisateurManagement utilisateurManagement;

    @MockBean
    private IRepositoryUtilisateur repositoryUtilisateur;

    @Test
    @DisplayName("Construction d'un Utilisateur, à partir d'un uid et d'un profil")
    public void enregistrerUtilisateur_should_call_repo_with_a_full_Utilisateur(){
        //giving
        String uid = "a19390";
        String nom = "DUPOND";
        String prenom = "Francois";
        String numeroLigne = "0102030405";
        String roleRecu = "type1";
        UtilisateurRoleEnum roleDomaine = UtilisateurRoleEnum.ROLE_TYPE1;

        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);
        Uo monUo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        monUo.setSiteExercice(siteExercice);
        Collaborateur monCollaborateur = new Collaborateur(uid,nom, prenom,numeroLigne,monUo);
        Utilisateur monUtilisateur = new Utilisateur(uid,nom,prenom,roleDomaine);

        Mockito.when(collaborateurManagement.findByUid(uid)).thenReturn(monCollaborateur);

        ArgumentCaptor<Utilisateur> valueCapture = ArgumentCaptor.forClass(Utilisateur.class);
//        Mockito.doReturn(valueCapture.getValue()).when(repositoryUtilisateur).creerUser(valueCapture.capture());

        //when
        utilisateurManagement.enregistrerUtilisateur(uid,roleRecu);

        //then
        Mockito.verify(repositoryUtilisateur).creerUser(valueCapture.capture());
        assertAll(
                () -> assertThat(valueCapture.getValue().getUid()).isEqualTo(monUtilisateur.getUid()),
                () -> assertThat(valueCapture.getValue().getNom()).isEqualTo(monUtilisateur.getNom()),
                () -> assertThat(valueCapture.getValue().getPrenom()).isEqualTo(monUtilisateur.getPrenom()),
                () -> assertThat(valueCapture.getValue().getUserRole()).isEqualTo(monUtilisateur.getUserRole()),
                () -> assertThat(valueCapture.getValue().getLogin()).isNotNull()
        );

    }

    @Test
    @DisplayName("nombre d'appel de CollaborateurManagement")
    public void enregistrerUtilisateur_should_call_collaborateurManagement_once(){
        //giving
        String uid = "a19390";
        String nom = "DUPOND";
        String prenom = "Francois";
        String numeroLigne = "0102030405";
        String roleRecu = "type1";
        UtilisateurRoleEnum roleDomaine = UtilisateurRoleEnum.ROLE_TYPE1;


        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);
        Uo monUo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        monUo.setSiteExercice(siteExercice);
        Collaborateur monCollaborateur = new Collaborateur(uid,nom, prenom,numeroLigne,monUo);

        Mockito.when(collaborateurManagement.findByUid(uid)).thenReturn(monCollaborateur);

        //when
        utilisateurManagement.enregistrerUtilisateur(uid,roleRecu);

        //then
        Mockito.verify(collaborateurManagement, Mockito.times(1)).findByUid(uid);

    }

    @Test
    @DisplayName("Ordre d'appel des services")
    public void enregistrerUtilisateur_should_call_collaborateurManagement_first(){
        //giving
        String uid = "a19390";
        String nom = "DUPOND";
        String prenom = "Francois";
        String numeroLigne = "0102030405";
        String roleRecu = "type1";


        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);
        Uo monUo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        monUo.setSiteExercice(siteExercice);
        Collaborateur monCollaborateur = new Collaborateur(uid,nom, prenom,numeroLigne,monUo);

        Mockito.when(collaborateurManagement.findByUid(uid)).thenReturn(monCollaborateur);
        InOrder inOrder = inOrder(collaborateurManagement,repositoryUtilisateur);

        //when
        utilisateurManagement.enregistrerUtilisateur(uid,roleRecu);

        //then
        inOrder(collaborateurManagement).verify(collaborateurManagement).findByUid(uid);
        inOrder(repositoryUtilisateur).verify(repositoryUtilisateur).creerUser(any(Utilisateur.class));

    }


    @Test
    @DisplayName("Rejet profil Utilisateur inconnu")
    public void wrong_profilUtilisateur_should_throw_badRequestException(){
        //giving
        String uid = "a19390";
        String nom = "DUPOND";
        String prenom = "Francois";
        String numeroLigne = "0102030405";
        String roleRecu = "type3";

        //when
            utilisateurManagement.enregistrerUtilisateur(uid,roleRecu);

        //then


    }

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
}
