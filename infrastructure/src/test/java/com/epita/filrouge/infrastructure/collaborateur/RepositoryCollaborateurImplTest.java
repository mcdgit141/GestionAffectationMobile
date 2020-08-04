package com.epita.filrouge.infrastructure.collaborateur;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.infrastructure.site.SiteExerciceEntity;
import com.epita.filrouge.infrastructure.uo.UoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest // permet de dire que l'on utilise h2 si trouve le package h2 dans le pom
//@Disabled
class RepositoryCollaborateurImplTest {

    private static final Long SITE_ID = 1L;
    private static final String CODE_SITE = "V2";
    private static final String NOM_SITE = "Valmy2";
    private static final String ADRESSE_POSTALE = "41, Rue de Valmy";
    private static final String CODE_POSTAL = "93100";
    private static final String VILLE = "MONTREUIL";
    private static final String PAYS = "FRANCE";
    private static final LocalDate DATE_CREATION = LocalDate.now();

    private static final Long UO_ID = 1L;
    private static final String CODE_UO = "SDI101";
    private static final String FONCTION_RATTACHEMENT = "BDDF IT";
    private static final String CODE_UO_PARENT = "SDI1";
    private static final String NOM_USAGE_UO = "DATAHUB";
    private static final String NOM_RESPONSABLE_UO = "Alfonse de la Renardiere";

    private CollaborateurEntity monCollaborateurPersiste;

    @Autowired
    RepositoryCollaborateurImpl repositoryCollaborateurImpl;

    @Autowired
    private TestEntityManager entityManager;

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
        entityManager.persistAndFlush(siteExerciceEntity);

        UoEntity uoEntity = new UoEntity();
        uoEntity.setCodeUo(CODE_UO);
        uoEntity.setFonctionRattachement(FONCTION_RATTACHEMENT);
        uoEntity.setCodeUoParent(CODE_UO_PARENT);
        uoEntity.setNomUsageUo(NOM_USAGE_UO);
        uoEntity.setNomResponsableUo(NOM_RESPONSABLE_UO);
        uoEntity.setSiteExercice(siteExerciceEntity);
        entityManager.persistAndFlush(uoEntity);

        CollaborateurEntity collaborateurEntity = new CollaborateurEntity();
        collaborateurEntity.setUid("425895");
        collaborateurEntity.setNom("Vivier");
        collaborateurEntity.setPrenom("D");
        collaborateurEntity.setNumeroLigne("0612345678");
        collaborateurEntity.setUo(uoEntity);
        monCollaborateurPersiste = entityManager.persist(collaborateurEntity);
    }

    @Test
    void shouldReturnACollaborateur () {
        // Given

        // When
        Collaborateur collaborateurRetour = repositoryCollaborateurImpl.findByUid("425895");

        // Then
        assertThat(collaborateurRetour.getNom()).isEqualTo("Vivier");
        assertThat(collaborateurRetour.getPrenom()).isEqualTo("D");
        assertThat(collaborateurRetour.getNumeroLigne()).isEqualTo("0612345678");
    }

    @Test
    void shouldReturnACollaborateur_WhenFindByNumeroLigne() {
        //Given

        //When
        Collaborateur collaborateurRetour = repositoryCollaborateurImpl.findByNumeroLigne("0612345678");

        // Then
        assertThat(collaborateurRetour.getNom()).isEqualTo("Vivier");
        assertThat(collaborateurRetour.getPrenom()).isEqualTo("D");
        assertThat(collaborateurRetour.getUid()).isEqualTo("425895");

    }

    @Test
    @DisplayName("Actualisation du numéro de ligne sur le collaborateur après affectation")
    public void should_update_collaborateur_with_numLigne_given_if_different(){
        //given

        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);
        Uo uo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        uo.setSiteExercice(siteExercice);

        Collaborateur monCollaborateur = new Collaborateur("425895", "Vivier", "D","0612345678",uo);
        String numeroLigne = "0670588845";

        //when
        repositoryCollaborateurImpl.miseAJourCollaborateur(monCollaborateur, numeroLigne);

        //then
        CollaborateurEntity collaborateurEntityLu = entityManager.find(CollaborateurEntity.class,monCollaborateurPersiste.getCollaborateurId());
        if (collaborateurEntityLu != null)
             {assertThat(collaborateurEntityLu.getNumeroLigne()).isEqualTo(numeroLigne);}
    }
}