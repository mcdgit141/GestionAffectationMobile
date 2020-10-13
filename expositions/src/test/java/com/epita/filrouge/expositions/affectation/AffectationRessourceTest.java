package com.epita.filrouge.expositions.affectation;

import com.epita.filrouge.application.affectation.IAffectationManagement;
import com.epita.filrouge.application.security.UserDetailServiceImpl;
import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.FiltresAffectation;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.domain.utilisateur.IRepositoryUtilisateur;
import com.epita.filrouge.expositions.collaborateur.CollaborateurDTO;
import com.epita.filrouge.expositions.collaborateur.CollaborateurDTOMapper;
import com.epita.filrouge.expositions.exception.MapperExceptionCode;
import com.epita.filrouge.expositions.iphone.IphoneDTO;
import com.epita.filrouge.expositions.iphone.IphoneDTOMapper;
import com.epita.filrouge.jwt.JwtAuthenticationEntryPoint;
import com.epita.filrouge.jwt.JwtRequestFilter;
import com.epita.filrouge.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({AffectationRessource.class, MapperExceptionCode.class
            , AffectationFullDTOMapper.class
//            , AffectationFullDTOMappertest.class
            , IphoneDTOMapper.class
            , CollaborateurDTOMapper.class
            , JwtUtils.class, JwtRequestFilter.class, JwtAuthenticationEntryPoint.class})
@DisplayName("Affectation Tests")
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
    private static final String AFFECTATION_NUMERO_STRING = "1";
    private static final LocalDate AFFECTATION_DATE = LocalDate.now();
    private static final String AFFECTATION_COMMENTAIRE = "Premiere affectation";
    private static final LocalDate AFFECTATION_DATEFIN = LocalDate.now();
    private static final String AFFECTATION_MOTIFFIN = "Vole";

    @MockBean
    private IRepositoryUtilisateur repositoryUtilisateur;

    @MockBean
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAffectationManagement affectationManagement;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void afterEach() {
        reset(affectationManagement);
    }

    @Nested
    @DisplayName("Creation")
    class test_create {

        @Test
        @WithMockUser(roles = {"TYPE2", "ADMIN"})
        // controle de l'AUTHENTIFICATION (Spring security), l'anotation @Secured est non indispensable dans le controller
        @DisplayName("V2 Doit retouner une ApplicationFullDTO")
        void doitRetournerUneFUllDTO() throws Exception {

            // Given
            AffectationFullDTO affectationFullDTOACreer = instancierUneAffectationFullDTO();

            Affectation affectationRetournee = instancierUneAffectation();


            when(affectationManagement.creerAffectation(COLLABORATEUR_UID, IPHONE_NUMEROSERIE, AFFECTATION_DATE,
                    COLLABORATEUR_NUMEROLIGNE, AFFECTATION_COMMENTAIRE)).thenReturn(affectationRetournee);

            String monObjetMapper = objectMapper.writeValueAsString(affectationFullDTOACreer);
//            When
            mockMvc.perform(MockMvcRequestBuilders.post("/gestaffectation/affectation/creation")//
                    .content(monObjetMapper) //
                    .contentType(MediaType.APPLICATION_JSON))
                    // Then
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.numeroAffectation").value(AFFECTATION_NUMERO))
                    .andExpect(jsonPath("$.commentaire").value(AFFECTATION_COMMENTAIRE))
                    .andExpect(jsonPath("$.collaborateur").isNotEmpty())
                    .andExpect(jsonPath("$.collaborateur.uid").value(COLLABORATEUR_UID))
                    .andExpect(jsonPath("$.iphone").isNotEmpty())
                    .andExpect(jsonPath("$.iphone.numeroSerie").value(IPHONE_NUMEROSERIE))
            ;
        }


        @Test
        @WithMockUser(roles = {"TYPE1"})
        @DisplayName("V2 Interdiction pour role TYPE1")
        void role_type1_ne_peux_pas_creer_affectation() throws Exception {

            // Given
            AffectationFullDTO affectationFullDTOACreer = instancierUneAffectationFullDTO();

            String monObjetMapper = objectMapper.writeValueAsString(affectationFullDTOACreer);

            //When
            mockMvc.perform(post("/gestaffectation/affectation/creation")//
                    .content(monObjetMapper) //
                    .contentType(MediaType.APPLICATION_JSON))
                    // Then
                    .andExpect(status().isForbidden());
        }


        @Test
        @WithMockUser(roles = {"TYPE2", "ADMIN"})
        @DisplayName("V2 Badrequest si l'uid est null")
        void WhenUidIsNullForACreation_ShouldReturnABadRequestExceptionWithMessage() throws Exception {
            //Given
            AffectationFullDTO affectationFullDTOACreer = instancierUneAffectationFullDTO();
            affectationFullDTOACreer.getCollaborateur().setUid(null);

            String monObjetMapper = objectMapper.writeValueAsString(affectationFullDTOACreer);

            //When
            String resultat = mockMvc.perform(post("/gestaffectation/affectation/creation")
                    .content(monObjetMapper)
                    .contentType(MediaType.APPLICATION_JSON))
                    //then
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();
            System.out.println("resultat = " + resultat);
            assertThat(resultat).contains("L'UID du collaborateur ne peut etre vide");

        }


        @Test
        @WithMockUser(roles = {"TYPE2", "ADMIN"})
        @DisplayName("V2 Badrequest si l'uid n'est pas sur 6 caractères")
        void WhenLengthOfUidIsNotSix_ShouldReturnABadRequestExceptionWithMessage() throws Exception {
            //Given

            AffectationFullDTO affectationFullDTOACreer = instancierUneAffectationFullDTO();
            affectationFullDTOACreer.getCollaborateur().setUid("ABCDEFG");

            String monObjetMapper = objectMapper.writeValueAsString(affectationFullDTOACreer);

            //When
            String resultat = mockMvc.perform(post("/gestaffectation/affectation/creation")
                    .content(monObjetMapper)
                    .contentType(MediaType.APPLICATION_JSON))
                    //then
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();
            assertThat(resultat).contains("L'UID du collaborateur n'est pas valide");
        }


    }

    @Nested
    @DisplayName("List")
    class test_list {
        @Test
//        @Disabled
        @WithMockUser(roles = {"TYPE2", "ADMIN"})
        @DisplayName("Doit bien renvoyer les affectations quand demande d'affichage avec filtres")
        void ShouldReturnAffectations_WhenFilters() throws Exception {
            //Given
            FiltresAffectation filtresAffectation = new FiltresAffectation();
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
        @WithMockUser(roles = {"TYPE2", "ADMIN"})
        @DisplayName("Doit transmettre la demande d'affichage avec les bons filtres")
        void ShouldCallWithTheFilters() throws Exception {
            //Given
            FiltresAffectation filtresAffectation = new FiltresAffectation();
            filtresAffectation.setUid(COLLABORATEUR_UID);

            Affectation affectationRetournee = instancierUneAffectation();
            List<Affectation> listeAffectationsRetournee = new ArrayList<>();
            listeAffectationsRetournee.add(affectationRetournee);

            ArgumentCaptor<FiltresAffectation> filtresAffectationArgumentCaptor = ArgumentCaptor.forClass(FiltresAffectation.class);

            when(affectationManagement.listerAffectation(any(FiltresAffectation.class))).thenReturn(listeAffectationsRetournee);

            String monObjetMapper = objectMapper.writeValueAsString(filtresAffectation);
//            verify(affectationManagement).listerAffectation(filtresAffectationArgumentCaptor.capture());//fait la capture
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
                            null, null, null, null, null, null, null, null);

        }

    }

    @Nested
    @DisplayName("Closing")
    class test_closing {

        @Test
        @WithMockUser(roles = {"TYPE1"})
        @DisplayName("Interdiction de clôturer une affectation pour le rôle TYPE1")
        void role_type1_ne_peux_pas_cloturer_une_affectation() throws Exception {

            // Given
            AffectationFullDTO affectationFullDTOACloturer = instancierUneAffectationFullDTO();

            String monObjetMapper = objectMapper.writeValueAsString(affectationFullDTOACloturer);

            //When
            mockMvc.perform(put("/gestaffectation/affectation/cloture")//
                    .content(monObjetMapper) //
                    .contentType(MediaType.APPLICATION_JSON))
                    // Then
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Levée d'une BadRequest exception si le body de la requête est incomplet")
        @WithMockUser(roles = "TYPE2")
        void affectationDTO_Uncomplete_should_throw_an_Exception() throws Exception {
            //given
            AffectationFullDTO affectationFullDTO = new AffectationFullDTO();
            affectationFullDTO.setNumeroAffectation(AFFECTATION_NUMERO_STRING);

            String monObjetMapper = objectMapper.writeValueAsString(affectationFullDTO);

            //when
            String resultat = mockMvc.perform(put("/gestaffectation/affectation/cloture")
                    .content(monObjetMapper)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse().getContentAsString();
            //then
            assertThat(resultat).contains("BAD REQUEST");

        }

        @Test
        @WithMockUser(roles = {"TYPE2"})
//        @Disabled("disabled le temps de la refote des DTO")
        @DisplayName("Doit appeler cloturerAffectation une seule fois")
        void role_type2_doitAppelerCloturerAffectationDeCoucheApplication() throws Exception {

            // Given

            AffectationFullDTO affectationFullDTOACloturer = instancierUneAffectationFullDTO();

            affectationFullDTOACloturer.setNumeroAffectation(AFFECTATION_NUMERO_STRING);
            affectationFullDTOACloturer.setMotifFin(AFFECTATION_MOTIFFIN);
            affectationFullDTOACloturer.setDateFin(AFFECTATION_DATEFIN);

            String monObjetMapper = objectMapper.writeValueAsString(affectationFullDTOACloturer);
            System.out.println("monObjetMapper = " + monObjetMapper);
            //When
            mockMvc.perform(MockMvcRequestBuilders.put("/gestaffectation/affectation/cloture")
                    .content(monObjetMapper)
                    .contentType(MediaType.APPLICATION_JSON))
                    // Then
                    .andExpect(status().isOk())
            ;
            verify(affectationManagement, Mockito.times(1)).cloturerAffectation(AFFECTATION_NUMERO, AFFECTATION_COMMENTAIRE, AFFECTATION_MOTIFFIN, AFFECTATION_DATEFIN);

        }
    }


    @Nested
    @DisplayName("Suppression avec RequestParam")
    class test_suppress_affectation_Requestparam {

        private static final String URL_ROOT = "/gestaffectation/affectation/suppression?";
        private static final String URL_ID = "&id=";
        private static final String URL_COMMENATIRE= "&commentaire=";
        private static final long NUMERO_AFFECTATION_SUPPRESSION = 303L;
        private static  final String COMMENTAIRE_SUPPRESSION = "Pour test";

        @Test
        @WithMockUser(roles = {"TYPE2", "ADMIN"})
        @DisplayName("Doit transmettre la demande de suppression avec le numero d'affectation")
        void ShouldCallWithTheNumeroAffectation_ToDelete() throws Exception {
            //Given

            String urlTemplate= URL_ROOT +
                    "id=" +  NUMERO_AFFECTATION_SUPPRESSION +
                    "&commentaire=" + COMMENTAIRE_SUPPRESSION;


            ArgumentCaptor<Long> numeroAffectationArgumentCaptor = ArgumentCaptor.forClass(Long.class);

            //When
            mockMvc.perform(delete(urlTemplate)
                    .contentType(MediaType.APPLICATION_JSON))
            ;

            //Then
            verify(affectationManagement).supprimerAffectation(numeroAffectationArgumentCaptor.capture());

            assertThat(numeroAffectationArgumentCaptor.getValue()).isEqualTo(NUMERO_AFFECTATION_SUPPRESSION);

        }


        @Test
        @DisplayName("Interdiction pour role TYPE1")
        @WithMockUser(roles = {"TYPE1"})
        void role_type1_ne_peux_pas_supprimer_affectation() throws Exception {

            // Given
            String urlTemplate= URL_ROOT +
                    "id=" +  NUMERO_AFFECTATION_SUPPRESSION +
                    "&commentaire=" + COMMENTAIRE_SUPPRESSION;

            //When
            mockMvc.perform(delete(urlTemplate)//
                    .contentType(MediaType.APPLICATION_JSON))
            // Then
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Levée d'une BadRequest exception si l'Id n'est pas présent")
        @WithMockUser(roles = "TYPE2")
        void AbsentId_should_throw_an_Exception() throws Exception {
            //given
            String urlTemplate= URL_ROOT +
                    "&commentaire=" + COMMENTAIRE_SUPPRESSION
                    ;

            //when
            mockMvc.perform(delete(urlTemplate)
                    .contentType(MediaType.APPLICATION_JSON))
                    //then
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Levée d'une BadRequest(409) exception si le commentaire est inférieur à 5 caractères")
        @WithMockUser(roles = "TYPE2")
        void TooShortCommentary_should_throw_an_Exception() throws Exception {
            //given
            String urlTemplate= URL_ROOT
                    + URL_ID + NUMERO_AFFECTATION_SUPPRESSION
                     + URL_COMMENATIRE + "\"aa\""
                    ;

            //when
            String resultat = mockMvc.perform(delete(urlTemplate)
                    .contentType(MediaType.APPLICATION_JSON))
                    //then
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            //then
            assertThat(resultat).contains("le commentaire doit etre d'au moins 5 caracteres");

        }

        @Test
        @DisplayName("Levée d'une Badrequest(400) exception si le commentaire est absent")
        @WithMockUser(roles = "TYPE2")
        void AbsentCommentary_should_throw_an_Exception() throws Exception {
            //given
            String urlTemplate= URL_ROOT
                    + URL_ID + NUMERO_AFFECTATION_SUPPRESSION
                    ;

            //when
            mockMvc.perform(delete(urlTemplate)
                    .contentType(MediaType.APPLICATION_JSON))
                    //then
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isBadRequest());


        }

    }


    private Affectation instancierUneAffectation() {
//        LocalDate dateRevouvelementAttentue = AFFECTATION_DATE.plusYears(2);

        SiteExercice siteExercice = new SiteExercice(CODE_SITE, NOM_SITE, ADRESSE_POSTALE, CODE_POSTAL, VILLE, PAYS, DATE_CREATION);
        Uo uo = new Uo(CODE_UO, FONCTION_RATTACHEMENT, CODE_UO_PARENT, NOM_USAGE_UO, NOM_RESPONSABLE_UO);
        uo.setSiteExercice(siteExercice);

        Collaborateur collaborateur = new Collaborateur(COLLABORATEUR_UID, COLLABORATEUR_NOM, COLLABORATEUR_PRENOM, COLLABORATEUR_NUMEROLIGNE, uo);

        ModeleIphone modeleIphone = new ModeleIphone(1L, MODELE_NOMMODELE);
        Iphone iphone = new Iphone(1L, IPHONE_NUMEROSERIE, IPHONE_PRIX, modeleIphone, IPHONE_ETAT);

        return new Affectation(AFFECTATION_NUMERO, AFFECTATION_DATE, AFFECTATION_COMMENTAIRE, collaborateur, iphone);

    }

    private AffectationFullDTO instancierUneAffectationFullDTO() {

        CollaborateurDTO.SiteExerciceDTO siteExerciceDTO = new CollaborateurDTO.SiteExerciceDTO();
        siteExerciceDTO.setCodeSite(CODE_SITE);
        siteExerciceDTO.setNomSite(NOM_SITE);
        siteExerciceDTO.setAdressePostale1(ADRESSE_POSTALE);
        siteExerciceDTO.setCodePostal(CODE_POSTAL);
        siteExerciceDTO.setVille(VILLE);
        siteExerciceDTO.setPays(PAYS);
        siteExerciceDTO.setDateCreation(DATE_CREATION);


        CollaborateurDTO.UoDTO uoDTO = new CollaborateurDTO.UoDTO();
        uoDTO.setCodeUo(CODE_UO);
        uoDTO.setFonctionRattachement(FONCTION_RATTACHEMENT);
        uoDTO.setCodeUoParent(CODE_UO_PARENT);
        uoDTO.setNomUsageUo(NOM_USAGE_UO);
        uoDTO.setNomResponsableUo(NOM_RESPONSABLE_UO);
        uoDTO.setSiteExercice(siteExerciceDTO);

        CollaborateurDTO collaborateurDTO = new CollaborateurDTO(COLLABORATEUR_UID, COLLABORATEUR_NOM, COLLABORATEUR_PRENOM, COLLABORATEUR_NUMEROLIGNE, uoDTO);


        IphoneDTO.ModeleIphoneDTO modeleIphoneDTO = new IphoneDTO.ModeleIphoneDTO(1L, MODELE_NOMMODELE);
        IphoneDTO iphoneDTO = new IphoneDTO(IPHONE_NUMEROSERIE, IPHONE_PRIX, modeleIphoneDTO, IPHONE_ETAT);

        AffectationFullDTO affectationFullDTO = new AffectationFullDTO();
        affectationFullDTO.setDateAffectation(AFFECTATION_DATE);
        affectationFullDTO.setCommentaire(AFFECTATION_COMMENTAIRE);
        affectationFullDTO.setCollaborateur(collaborateurDTO);
        affectationFullDTO.setIphone(iphoneDTO);
        return affectationFullDTO;

    }
}

