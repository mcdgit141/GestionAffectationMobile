package com.epita.filrouge.application.utilisateur;


import com.epita.filrouge.application.collaborateur.ICollaborateurManagement;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.exception.AllReadyExistException;
import com.epita.filrouge.domain.exception.BadRequestException;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.domain.utilisateur.IRepositoryUtilisateur;
import com.epita.filrouge.domain.utilisateur.Utilisateur;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.predicate;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;


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
    @DisplayName("Rejet création d'un utilisateur avec un uid déjà utilisé")
    public void enregistrerUtilisateur_should_fail_with_already_existing_uid(){
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
        Mockito.when(repositoryUtilisateur.rechercherUserParUid(any(String.class))).thenReturn(monUtilisateur);

        //when + then
        assertThatThrownBy(
                () -> {utilisateurManagement.enregistrerUtilisateur(uid,roleRecu);}
        ).isInstanceOf(AllReadyExistException.class).hasMessageContaining("Un Utilisateur existe déjà avec l'uid : " + uid);
    }

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
        Mockito.when(repositoryUtilisateur.rechercherUserParUid(uid)).thenThrow(NotFoundException.class);

        ArgumentCaptor<Utilisateur> valueCapture = ArgumentCaptor.forClass(Utilisateur.class);
//        Mockito.doReturn(valueCapture.getValue()).when(repositoryUtilisateur).creerUser(valueCapture.capture());

        //when
        utilisateurManagement.enregistrerUtilisateur(uid,roleRecu);

        //then
        Mockito.verify(repositoryUtilisateur).enregistrerUtilisateur(valueCapture.capture());
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
        Mockito.when(repositoryUtilisateur.rechercherUserParUid(uid)).thenThrow(NotFoundException.class);
        InOrder inOrder = inOrder(collaborateurManagement,repositoryUtilisateur);

        //when
        utilisateurManagement.enregistrerUtilisateur(uid,roleRecu);

        //then
        inOrder(collaborateurManagement).verify(collaborateurManagement).findByUid(uid);
        inOrder(repositoryUtilisateur).verify(repositoryUtilisateur).rechercherUserParUid(any(String.class));
        inOrder(repositoryUtilisateur).verify(repositoryUtilisateur).enregistrerUtilisateur(any(Utilisateur.class));

    }


    @Test
    @DisplayName("Rejet profil Utilisateur inconnu")
    public void wrong_profilUtilisateur_should_throw_badRequestException(){
        //giving
        String uid = "a19390";
        String roleRecu = "type3";

        //when + then
        assertThatThrownBy(
                () -> {utilisateurManagement.enregistrerUtilisateur(uid,roleRecu);}
                ).isInstanceOf(BadRequestException.class).hasMessageContaining("role utilisateur transmis inconnu");

    }


    @Test
    @DisplayName("NotFoundException sur la suppression d'un utilisateur inconnu")
    public void supprimerUtilisateur_should_throw_Exception_if_user_dont_exists(){
        //giving
        String uid = "monUid";

        Mockito.when(repositoryUtilisateur.rechercherUserParUid(any(String.class))).thenThrow(new NotFoundException("toto"));

        //when + then
        assertThatThrownBy(
                () -> {utilisateurManagement.supprimerUtilisateur(uid);}
        ).isInstanceOf(NotFoundException.class);

    }

    @Test
    public void supprimerUtilisateur_should_call_repo_giving_an_existing_uid(){
        //giving
        String uid = "a19390";
        String nom = "KAMDEM";
        String prenom = "Leopold";
        UtilisateurRoleEnum roleUser = UtilisateurRoleEnum.ROLE_TYPE1;

        Utilisateur utilisateurTrouve = new Utilisateur(uid,nom,prenom,roleUser);
        Mockito.when(repositoryUtilisateur.rechercherUserParUid(uid)).thenReturn(utilisateurTrouve);
        ArgumentCaptor<Utilisateur> valueCapture = ArgumentCaptor.forClass(Utilisateur.class);

        //when
        utilisateurManagement.supprimerUtilisateur(uid);

        //then
        Mockito.verify(repositoryUtilisateur).supprimerUser(valueCapture.capture());

        assertAll(
                () -> assertThat(valueCapture.getValue().getNom()).isEqualTo(nom),
                () -> assertThat(valueCapture.getValue().getPrenom()).isEqualTo(prenom),
                () -> assertThat(valueCapture.getValue().getUserRole()).isEqualTo(roleUser)
                );

    }

    @Test
    @DisplayName("modifierUtilisateur : Appel du repo avec le mdp chiffré")
    public void modifierUtilisateur_should_call_repo_with_an_encrypted_password(){
        //given
        String uid = "a19390";
        String mdp = "monMotDePasse";
        String nom = "KAMDEM";
        String prenom = "Leopold";
        UtilisateurRoleEnum roleUser = UtilisateurRoleEnum.ROLE_TYPE1;

        Utilisateur utilisateurTrouve = new Utilisateur(uid,nom,prenom,roleUser);
        Mockito.when(repositoryUtilisateur.rechercherUserParUid(uid)).thenReturn(utilisateurTrouve);
        ArgumentCaptor<Utilisateur> valueCapture = ArgumentCaptor.forClass(Utilisateur.class);

        //when
        utilisateurManagement.modifierMdpUtilisateur(uid,mdp);
        //then
        Mockito.verify(repositoryUtilisateur).enregistrerUtilisateur(valueCapture.capture());
        assertAll(
                () -> assertThat(valueCapture.getValue().getPassword()).isNotEqualTo(mdp)
        );

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
