package com.epita.filrouge.application.utilisateur;


import com.epita.filrouge.application.collaborateur.ICollaborateurManagement;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.domain.utilisateur.IRepositoryUtilisateur;
import com.epita.filrouge.domain.utilisateur.Utilisateur;
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
    public void enregistrerUtilisateur_should_call_repo_with_a_Full_Utilisateur(){
        //giving
        String uid = "a19390";
        String nom = "DUPOND";
        String prenom = "Francois";
        String numeroLigne = "0102030405";
        UtilisateurRoleEnum role = UtilisateurRoleEnum.ROLE_TYPE1;

        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);
        Uo monUo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        monUo.setSiteExercice(siteExercice);
        Collaborateur monCollaborateur = new Collaborateur(uid,nom, prenom,numeroLigne,monUo);

        Utilisateur monUtilisateur = new Utilisateur(uid,nom,prenom,role);

        Mockito.when(collaborateurManagement.findByUid(uid)).thenReturn(monCollaborateur);

        //when
        utilisateurManagement.enregistrerUtilisateur(uid,role);

        //then
        Mockito.verify(collaborateurManagement, Mockito.times(1)).findByUid(uid);
        Mockito.inOrder(collaborateurManagement).verify(collaborateurManagement).findByUid(uid);

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
