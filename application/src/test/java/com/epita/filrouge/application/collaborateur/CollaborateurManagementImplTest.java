package com.epita.filrouge.application.collaborateur;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.collaborateur.IRepositoryCollaborateur;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { CollaborateurManagementImpl.class } )
class CollaborateurManagementImplTest {
    
    @MockBean
    IRepositoryCollaborateur repositoryCollaborateur;
    
    @Autowired
    ICollaborateurManagement collaborateurManagement;
    
    @Test
    @DisplayName("recherche d'un collaborateur à partir d'un uid existant")
    void findByUid_should_return_a_Collaborateur_giving_an_existing_uid(){
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
        
        Collaborateur collaborateur = new Collaborateur(uid,nom,prenom,numeroLigne,monUo);
        Mockito.when(repositoryCollaborateur.findByUid(any(String.class))).thenReturn(collaborateur);
        
        //when
        Collaborateur collaborateurTrouve = collaborateurManagement.findByUid(uid);
        
        //then
        assertAll(
                () -> assertThat(collaborateurTrouve.getUid()).isEqualTo(collaborateur.getUid()),
                () -> assertThat(collaborateurTrouve.getNom()).isEqualTo(collaborateur.getNom()),
                () -> assertThat(collaborateurTrouve.getPrenom()).isEqualTo(collaborateur.getPrenom()),
                () -> assertThat(collaborateurTrouve.getNumeroLigne()).isEqualTo(collaborateur.getNumeroLigne())
        );
        
    }
    
    @Test
    @DisplayName("NotFoutExeption si uid inexistant")
    void findByUid_should_fail_with_an_unknown_uid(){
        //giving
        String uid = "a19390";
        String wrongUid = "b12345";
        String nom = "DUPOND";
        String prenom = "Francois";
        String numeroLigne = "0102030405";
        String roleRecu = "type1";
        UtilisateurRoleEnum roleDomaine = UtilisateurRoleEnum.ROLE_TYPE1;

        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);
        Uo monUo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        monUo.setSiteExercice(siteExercice);

        Collaborateur collaborateur = new Collaborateur(uid,nom,prenom,numeroLigne,monUo);
        Mockito.when(repositoryCollaborateur.findByUid(uid)).thenReturn(collaborateur);

        //when + then
        assertThatThrownBy(
                        () -> {collaborateurManagement.findByUid(wrongUid);}
        ).isInstanceOf(NotFoundException.class).hasMessageContaining("Le collaborateur par recherche sur l'UID suivant est non trouvé");

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
