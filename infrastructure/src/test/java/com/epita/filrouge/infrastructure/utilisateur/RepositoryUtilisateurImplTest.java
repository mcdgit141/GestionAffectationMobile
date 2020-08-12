package com.epita.filrouge.infrastructure.utilisateur;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.domain.utilisateur.Utilisateur;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;
import com.epita.filrouge.infrastructure.affectation.RepositoryAffectationImpl;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntityMapper;
import com.epita.filrouge.infrastructure.site.SiteExerciceEntity;
import com.epita.filrouge.infrastructure.site.SiteExerciceEntityMapper;
import com.epita.filrouge.infrastructure.uo.UoEntity;
import com.epita.filrouge.infrastructure.uo.UoEntityMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sun.security.x509.OtherName;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepositoryUtilisateurImplTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    RepositoryUtilisateurImpl repositoryUtilisateur;

    @Autowired
    private UtilisateurMapper utilisateurMapper;

    @Autowired
    private CollaborateurEntityMapper collaborateurEntityMapper;
    @Autowired
    private SiteExerciceEntityMapper siteExerciceEntityMapper;
    @Autowired
    private UoEntityMapper uoEntityMapper;


    private Utilisateur monUtitlisateur;
    private UtilisateurEntity utilisateurEntityPersiste;
    private Collaborateur monCollaborateur;
    private CollaborateurEntity collaborateurEntityPersiste;
    private SiteExercice monSiteExercice;
    private SiteExerciceEntity siteExerciceEntityPersiste;
    private Uo monUo;
    private UoEntity uoEntityPersiste;


    @BeforeEach
    public void init(){
        monSiteExercice = new SiteExercice(CODE_SITE, NOM_SITE, ADRESSE_POSTALE, CODE_POSTAL, VILLE, PAYS, DATE_CREATION);
        SiteExerciceEntity siteExerciceEntity = siteExerciceEntityMapper.mapToEntity(monSiteExercice);
        siteExerciceEntityPersiste = entityManager.persistAndFlush(siteExerciceEntity);

        monUo = new Uo(CODE_UO, FONCTION_RATTACHEMENT, CODE_UO_PARENT, NOM_USAGE_UO, NOM_RESPONSABLE_UO);
        monUo.setSiteExercice(monSiteExercice);
        UoEntity uoEntity = uoEntityMapper.mapToEntity(monUo);
        uoEntityPersiste = entityManager.persistAndFlush(uoEntity);

        monCollaborateur = new Collaborateur(UID, NOM, PRENOM,"0102030405",monUo);
        CollaborateurEntity monCollaborateurEntity = collaborateurEntityMapper.mapToEntity(monCollaborateur);
        collaborateurEntityPersiste = entityManager.persistAndFlush(monCollaborateurEntity);

        UtilisateurRoleEnum roleUtilisateur = UtilisateurRoleEnum.ROLE_TYPE1;
        monUtitlisateur = new Utilisateur(UID,NOM,PRENOM,roleUtilisateur);
        UtilisateurEntity utilisateurEntity = utilisateurMapper.mapToEntity(monUtitlisateur);
        utilisateurEntityPersiste = entityManager.persistAndFlush(utilisateurEntity);

    }


    @Test
    @DisplayName("enregistrerUtilisateur : Insertion d'un nouvel utlisateur en base s'il n'existe pas déjà")
    public void enregistrerUtilisateur_should_insert_an_Entity_when_user_dont_exists(){
        //given
        Utilisateur utilisateurACreer = new Utilisateur("b12345","DUPOND","Francois",UtilisateurRoleEnum.ROLE_ADMIN);

        List<UtilisateurEntity> utilisateurEntityRecherche = entityManager.getEntityManager()
                .createQuery("select u from UtilisateurEntity u where uid = 'b12345' ")
                .getResultList();

        //when
        repositoryUtilisateur.enregistrerUtilisateur(utilisateurACreer);

        //then
        List<UtilisateurEntity> utilisateurEntityTrouve = entityManager.getEntityManager()
                .createQuery("select u from UtilisateurEntity u where uid = 'b12345' ")
                .getResultList();

        assertAll(
                () -> assertThat(utilisateurEntityRecherche.size()).isZero(),
                () -> assertThat(utilisateurEntityTrouve.size()).isOne()
        );

    }

    @Test
    @DisplayName("enregistrerUtilisateur : Mise à jour de l'utlisateur en base s'il existe déjà")
    public void enregistrerUtilisateur_should_update_Entity_when_user_exists(){
        //given
        Utilisateur utilisateurAModifier = new Utilisateur(UID,NOM,PRENOM,UtilisateurRoleEnum.ROLE_ADMIN);

        List<UtilisateurEntity> utilisateurEntityAvant = entityManager.getEntityManager()
                .createQuery("select u from UtilisateurEntity u where uid = 'a19390' ")
                .getResultList();


        //when
        repositoryUtilisateur.enregistrerUtilisateur(utilisateurAModifier);


        //then
        List<UtilisateurEntity> utilisateurEntityApres = entityManager.getEntityManager()
                .createQuery("select u from UtilisateurEntity u where uid = 'a19390' ")
                .getResultList();


        assertAll(
                () -> assertThat(utilisateurEntityAvant.size()).isOne(),
                () -> assertThat(utilisateurEntityApres.size()).isOne()

        );

    }

    @Test
    @DisplayName("Creation d'un UtilisateurEntity A partir d'un Utilisateur")
    public void creerUser_should_save_an_UtilisateurEntity(){
        //giving
        Utilisateur monUtitlisateurACreer = new Utilisateur("b12345","DUPOND","Francois",UtilisateurRoleEnum.ROLE_ADMIN);
        //when
        repositoryUtilisateur.creerUser(monUtitlisateurACreer);

        //then
        UtilisateurEntity utilisateurEntityCree = (UtilisateurEntity) entityManager.getEntityManager()
                                    .createQuery("select u from UtilisateurEntity u where uid = 'b12345' ")
                                    .getSingleResult();

        assertAll(
                () -> assertThat(utilisateurEntityCree).isNotNull(),
                () -> assertThat(utilisateurEntityCree.getPassword()).isEqualTo(monUtitlisateurACreer.getPassword()),
                () -> assertThat(utilisateurEntityCree.getLogin()).isEqualTo(monUtitlisateurACreer.getLogin()),
                () -> assertThat(utilisateurEntityCree.getUserRole()).isEqualTo(monUtitlisateurACreer.getUserRole())
                );
    }

    @Test
    @DisplayName("Suppression d'un utilisateur existant")
    public void deleteUser_should_delete_corresponding_entity(){
        //giving BeforeEach


        //when
        repositoryUtilisateur.supprimerUser(monUtitlisateur);

        //then
        assertThat(entityManager.find(UtilisateurEntity.class, utilisateurEntityPersiste.getId())).isNull();

    }

    @Test
    @DisplayName("NotFoundException si l'utilisateurEntity A supprimer n'est pas trouvé")
    public void deleteUser_should_throw_Exception_if_entity_do_not_exist(){
        //giving BeforeEach
        Utilisateur mauvaisUtilisateur = new Utilisateur("b12345","dupond","francois",UtilisateurRoleEnum.ROLE_ADMIN);

        //when + then
        assertThatThrownBy(
                () -> {repositoryUtilisateur.supprimerUser(mauvaisUtilisateur);}
                ).isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("recherche d'un utilisateur par son login")
    public void rechercherUser_should_return_an_entity_giving_an_existing_login(){
        //giving BeforeEach

        //when
        Utilisateur utilisateurTrouve = repositoryUtilisateur.rechercherUser(monUtitlisateur.getLogin());

        //then
        assertAll(
                () -> assertThat(utilisateurTrouve.getUid()).isEqualTo(utilisateurEntityPersiste.getUid()),
                () -> assertThat(utilisateurTrouve.getNom()).isEqualTo(collaborateurEntityPersiste.getNom()),
                () -> assertThat(utilisateurTrouve.getPrenom()).isEqualTo(collaborateurEntityPersiste.getPrenom()),
                () -> assertThat(utilisateurTrouve.getLogin()).isEqualTo(utilisateurEntityPersiste.getLogin())
        );
    }

    @Test
    @DisplayName("recherche d'un Utilisateur par l'uid associé")
    public void rechercherUserParUid_should_return_an_UtilisateurEntity_giving_an_existing_Uid(){

        //giving the existing DB

        //when
        Utilisateur utilisateur = repositoryUtilisateur.rechercherUserParUid(UID);
        //then
        assertThat(utilisateur).isNotNull();
    }


    //Collaborateur/Utilisateur
    private final String UID = "a19390";
    private final String NOM = "KAMDEM";
    private final String PRENOM = "Leopold";
    //Site
    private final String CODE_SITE = "V2";
    private final String NOM_SITE = "Valmy2";
    private final String ADRESSE_POSTALE = "41, Rue de Valmy";
    private final String CODE_POSTAL = "93100";
    private final String VILLE = "MONTREUIL";
    private final String PAYS = "FRANCE";
    private final LocalDate DATE_CREATION = LocalDate.now();
    //uo
    private final String CODE_UO = "SDI101";
    private final String FONCTION_RATTACHEMENT = "BDDF IT";
    private final String CODE_UO_PARENT = "SDI1";
    private final String NOM_USAGE_UO = "DATAHUB";
    private final String NOM_RESPONSABLE_UO = "Alfonse de la Renardiere";
}
