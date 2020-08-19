package com.epita.filrouge.expositions.affectation;

import com.epita.filrouge.application.affectation.IAffectationManagement;
import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.FiltresAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.expositions.exception.MapperExceptionCode;
import com.epita.filrouge.expositions.utilisateur.UtilisateurDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({AffectationRessource.class, MapperExceptionCode.class})

class AffectationRessourceTest {
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
    private static final LocalDate AFFECTATION_DATEFIN = LocalDate.now();
    private static final String AFFECTATION_MOTIFFIN = "Vole";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAffectationManagement affectationManagement;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = {"TYPE2","ADMIN"})  // controle de l'AUTHENTIFICATION (Spring security), l'anotation @Secured est non indispensable dans le controller
    @DisplayName("Doit appeler le saveAffectation de la couche application avec les bons paramètres")
    void role_type2_et_admin_doitAppelerSaveDeCoucheApplication() throws Exception {

        // Given
        AffectationDTO affectationDTO = new AffectationDTO();
        affectationDTO.setCollaborateurUid(COLLABORATEUR_UID);
        affectationDTO.setIphoneNumeroSerie(IPHONE_NUMEROSERIE);
        affectationDTO.setAffectationDate(AFFECTATION_DATE);
        affectationDTO.setCollaborateurNumeroLigne(COLLABORATEUR_NUMEROLIGNE);
        affectationDTO.setAffectationCommentaire(AFFECTATION_COMMENTAIRE);

        Affectation affectationRetournee = instancierUneAffectation();

        when(affectationManagement.creerAffectation(COLLABORATEUR_UID, IPHONE_NUMEROSERIE,AFFECTATION_DATE,
                                                    COLLABORATEUR_NUMEROLIGNE,AFFECTATION_COMMENTAIRE)).thenReturn(affectationRetournee);

        String monObjetMapper = objectMapper.writeValueAsString(affectationDTO);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/gestaffectation/affectation/creation")//
                .content(monObjetMapper) //
                .contentType(MediaType.APPLICATION_JSON))
         // Then
                .andExpect(status().isCreated())    // controle de l'AUTORISATION (spring security)
                .andExpect(jsonPath("$.collaborateur").isNotEmpty())
                .andExpect(jsonPath("$.collaborateur.uid").value(COLLABORATEUR_UID))
                .andExpect(jsonPath("$.iphone").isNotEmpty())
                .andExpect(jsonPath("$.iphone.numeroSerie").value(IPHONE_NUMEROSERIE))
                .andExpect(jsonPath("$.commentaire").value(AFFECTATION_COMMENTAIRE))
                ;
    }

    @Test
    @WithMockUser(roles = {"TYPE1"})
    @DisplayName("Interdiction creation affectation pour role TYPE1")
    void role_type1_ne_peux_pas_creer_affectation() throws Exception {

        // Given
        AffectationDTO affectationDTO = new AffectationDTO();
        affectationDTO.setCollaborateurUid(COLLABORATEUR_UID);
        affectationDTO.setIphoneNumeroSerie(IPHONE_NUMEROSERIE);
        affectationDTO.setAffectationDate(AFFECTATION_DATE);
        affectationDTO.setCollaborateurNumeroLigne(COLLABORATEUR_NUMEROLIGNE);
        affectationDTO.setAffectationCommentaire(AFFECTATION_COMMENTAIRE);

        String monObjetMapper = objectMapper.writeValueAsString(affectationDTO);

        //When
        mockMvc.perform(post("/gestaffectation/affectation/creation")//
                .content(monObjetMapper) //
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"TYPE1","ADMIN"})
    @DisplayName("cloturer affectation: Interdiction de clôturer une affectation pour les rôles TYPE1 et Admin")
    void role_type1_et_admin_ne_peuvent_pas_cloturer_une_affectation() throws Exception {

        // Given

        AffectationDTO affectationDTO = new AffectationDTO();
        affectationDTO.setNumeroAffectation(AFFECTATION_NUMERO);
        affectationDTO.setDateFin(AFFECTATION_DATEFIN);
        affectationDTO.setMotifFin(AFFECTATION_MOTIFFIN);
        affectationDTO.setAffectationCommentaire(AFFECTATION_COMMENTAIRE);

        String monObjetMapper = objectMapper.writeValueAsString(affectationDTO);

        //When
        mockMvc.perform(post("/gestaffectation/affectation/cloture")//
                .content(monObjetMapper) //
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("cloturer affectation: Levée d'une BadRequest exception si le body de la requête est incomplet")
    @WithMockUser(roles = "TYPE2")
    public void affectationDTO_Uncomplete_should_throw_an_Exception() throws Exception {
        //given
        AffectationDTO affectationDTO = new AffectationDTO();
        affectationDTO.setNumeroAffectation(AFFECTATION_NUMERO);

        String monObjetMapper = objectMapper.writeValueAsString(affectationDTO);

        //when
        String resultat = mockMvc.perform(post("/gestaffectation/affectation/cloture")
                .content(monObjetMapper)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        //then
        assertThat(resultat.contains("BAD REQUEST")).isTrue();

    }

    @Test
    @WithMockUser(roles = {"TYPE2"})  // controle de l'AUTHENTIFICATION (Spring security), l'anotation @Secured est non indispensable dans le controller
    @DisplayName("cloturer affectation: Doit appeler cloturerAffectation une seule fois")
    void role_type2_doitAppelerCloturerAffectationDeCoucheApplication() throws Exception {

        // Given
        AffectationDTO affectationDTO = new AffectationDTO();
        affectationDTO.setCollaborateurUid(COLLABORATEUR_UID);
        affectationDTO.setIphoneNumeroSerie(IPHONE_NUMEROSERIE);
        affectationDTO.setAffectationDate(AFFECTATION_DATE);
        affectationDTO.setCollaborateurNumeroLigne(COLLABORATEUR_NUMEROLIGNE);
        affectationDTO.setAffectationCommentaire(AFFECTATION_COMMENTAIRE);

        Affectation affectationRetournee = instancierUneAffectation();

        when(affectationManagement.creerAffectation(COLLABORATEUR_UID, IPHONE_NUMEROSERIE,AFFECTATION_DATE,
                COLLABORATEUR_NUMEROLIGNE,AFFECTATION_COMMENTAIRE)).thenReturn(affectationRetournee);

        AffectationDTO affectationDTOCloture = new AffectationDTO();
        affectationDTOCloture.setNumeroAffectation(AFFECTATION_NUMERO);
        affectationDTOCloture.setDateFin(AFFECTATION_DATEFIN);
        affectationDTOCloture.setMotifFin(AFFECTATION_MOTIFFIN);
        affectationDTOCloture.setAffectationCommentaire(AFFECTATION_COMMENTAIRE);

        String monObjetMapper = objectMapper.writeValueAsString(affectationDTOCloture);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/gestaffectation/affectation/cloture")
                .content(monObjetMapper)
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
        ;
        verify(affectationManagement, Mockito.times(1)).cloturerAffectation(AFFECTATION_NUMERO,AFFECTATION_COMMENTAIRE,AFFECTATION_MOTIFFIN,AFFECTATION_DATEFIN);
        ;
    }

    @Test
    @WithMockUser(roles = {"TYPE2","ADMIN"})
    @DisplayName("Doit bien renvoyer les affectations quand demande d'affichage avec filtres")
    void ShouldReturnAffectations_WhenFilters() throws Exception {
        //Given
        FiltresAffectation filtresAffectation= new FiltresAffectation();
        filtresAffectation.setUid("666999");

        Affectation affectationRetournee = instancierUneAffectation();
        List<Affectation> listeAffectationsRetournee = new ArrayList<>();
        listeAffectationsRetournee.add(affectationRetournee);

        when(affectationManagement.listerAffectation(any(FiltresAffectation.class))).thenReturn(listeAffectationsRetournee);

        String monObjetMapper = objectMapper.writeValueAsString(filtresAffectation);
        //When
        mockMvc.perform(post("/gestaffectation/affectation/liste")//
                .content(monObjetMapper) //
                .contentType(MediaType.APPLICATION_JSON))
        //Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroAffectation").value(AFFECTATION_NUMERO))
                .andExpect(jsonPath("$[0].dateAffectation").value(AFFECTATION_DATE.toString()))
                .andExpect(jsonPath("$[0].dateRenouvellementPrevue").value(AFFECTATION_DATE.plusYears(2).toString()))
                .andExpect(jsonPath("$[0].dateFin").isEmpty())
                .andExpect(jsonPath("$[0].commentaire").value(AFFECTATION_COMMENTAIRE))
                .andExpect(jsonPath("$[0].motifFin").isEmpty())
                .andExpect(jsonPath("$[0].collaborateur.uid").value(COLLABORATEUR_UID))
                ;
        verify(affectationManagement, Mockito.times(1)).listerAffectation(any(FiltresAffectation.class));

    }

    @Test
    @WithMockUser(roles = {"TYPE2","ADMIN"})
    @DisplayName("Doit transmettre la demande d'affichage avec les bons filtres")
    void ShouldCallWithTheFilters() throws Exception {
        //Given
        FiltresAffectation filtresAffectation= new FiltresAffectation();
        filtresAffectation.setUid(COLLABORATEUR_UID);

        Affectation affectationRetournee = instancierUneAffectation();
        List<Affectation> listeAffectationsRetournee = new ArrayList<>();
        listeAffectationsRetournee.add(affectationRetournee);

        ArgumentCaptor<FiltresAffectation> filtresAffectationArgumentCaptor = ArgumentCaptor.forClass(FiltresAffectation.class);

        when(affectationManagement.listerAffectation(any(FiltresAffectation.class))).thenReturn(listeAffectationsRetournee);

        String monObjetMapper = objectMapper.writeValueAsString(filtresAffectation);
        //When
        mockMvc.perform(post("/gestaffectation/affectation/liste")//
                .content(monObjetMapper) //
                .contentType(MediaType.APPLICATION_JSON))
        //Then
                .andExpect(status().isOk())
                ;
        verify(affectationManagement).listerAffectation(filtresAffectationArgumentCaptor.capture());//fait la capture

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
                        null,null,null,null,null,null,null,null);

    }

    @Test
    @DisplayName("Interdiction suppression affectation pour role TYPE1")
    @WithMockUser(roles = {"TYPE1"})
    void role_type1_ne_peux_pas_supprimer_affectation() throws Exception {

        // Given
        Long numeroAffectation = 303L;
        String monObjetMapper = objectMapper.writeValueAsString(numeroAffectation);

        //When
        mockMvc.perform(post("/gestaffectation/affectation/suppression")//
                .content(monObjetMapper) //
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("suppression affectation: Levée d'une BadRequest exception si le body n'est pas un Long")
    @WithMockUser(roles = "TYPE2")
    void bodyWithWrongType_should_throw_an_Exception() throws Exception {
        //given
        String numeroAffection = "wrong";
        String monObjetMapper = objectMapper.writeValueAsString(numeroAffection);

        //when
        mockMvc.perform(post("/gestaffectation/affectation/suppression")
                .content(monObjetMapper)
                .contentType(MediaType.APPLICATION_JSON))
        //then
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(roles = {"TYPE2","ADMIN"})
    @DisplayName("Suppression affectation : Doit transmettre la demande de suppression le numero d'affectation")
    void ShouldCallWithTheNumeroAffectation_ToDelete() throws Exception {
        //Given
        Long numeroAffectation = 303L;
        String monObjetMapper = objectMapper.writeValueAsString(numeroAffectation);

        ArgumentCaptor<Long> numeroAffectationArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        //When
        mockMvc.perform(post("/gestaffectation/affectation/suppression")
                .content(monObjetMapper)
                .contentType(MediaType.APPLICATION_JSON))
        ;
//                //Then
        verify(affectationManagement).supprimerAffectation(numeroAffectationArgumentCaptor.capture());//fait la capture

        assertThat(numeroAffectationArgumentCaptor.getValue()).isEqualTo(numeroAffectation);

    }

    private Affectation instancierUneAffectation() {
        LocalDate dateRevouvelementAttentue = AFFECTATION_DATE.plusYears(2);

        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);
        Uo uo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        uo.setSiteExercice(siteExercice);

        Collaborateur collaborateur = new Collaborateur( COLLABORATEUR_UID, COLLABORATEUR_NOM, COLLABORATEUR_PRENOM, COLLABORATEUR_NUMEROLIGNE,uo);

        ModeleIphone modeleIphone = new ModeleIphone(1L, MODELE_NOMMODELE);
        Iphone iphone = new Iphone(1L, IPHONE_NUMEROSERIE, IPHONE_PRIX, modeleIphone, IPHONE_ETAT);

        return new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

    }



}

