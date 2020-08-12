package com.epita.filrouge.expositions.utilisateur;

import com.epita.filrouge.application.utilisateur.IUtilisateurManagement;
import com.epita.filrouge.expositions.exception.MapperExceptionCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest({UtilisateurRessource.class, MapperExceptionCode.class})
//@Disabled
public class UtilisateurRessourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUtilisateurManagement utilisateurManagement;

    @Autowired
    private ObjectMapper objectMapper;

    private String monObjetMapper;


    @Test
    @DisplayName("creerUtilisateur : Autorisé pour le profil ADMIN")
    @WithMockUser(roles = "ADMIN")
    public void role_admin_peut_creer_des_utilisateurs() throws Exception {

        // Given
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setUid("a19390");
        utilisateurDTO.setRoleUtilisateur("type1");

        monObjetMapper = objectMapper.writeValueAsString(utilisateurDTO);

        //When
        mockMvc.perform(post("/gestaffectation/utilisateur/create")//
                .content(monObjetMapper) //
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isCreated());    // controle de l'AUTORISATION (spring security)

    }

    @Test
    @DisplayName("creerUtilisateur : Interdiction aux profil autre que ADMIN")
    @WithMockUser(roles = {"TYPE1","TYPE2"})
    public void role_autre_que_admin_ne_peut_pas_creer_des_utilisateurs() throws Exception {

        // Given
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setUid("a19390");
        utilisateurDTO.setRoleUtilisateur("type1");

        monObjetMapper = objectMapper.writeValueAsString(utilisateurDTO);

        //When
        mockMvc.perform(post("/gestaffectation/utilisateur/create")//
                .content(monObjetMapper) //
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isForbidden());    // controle de l'AUTORISATION (spring security)

        //then
        Mockito.verify(utilisateurManagement,Mockito.times(0)).enregistrerUtilisateur(utilisateurDTO.getUid(),utilisateurDTO.getRoleUtilisateur());

    }

    @Test
    @DisplayName("creerUtilisateur :  Passage de paramètre entre le Body de la requete Http et le service de création")
    @WithMockUser(roles = "ADMIN")
    public void creerUtilisateur_should_call_UtilisateurManegement_Once() throws Exception {
        //given
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setUid("a19390");
        utilisateurDTO.setRoleUtilisateur("type1");

        monObjetMapper = objectMapper.writeValueAsString(utilisateurDTO);

        //when
        mockMvc.perform(post("/gestaffectation/utilisateur/create")
                    .content(monObjetMapper)
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        //then
        Mockito.verify(utilisateurManagement,Mockito.times(1)).enregistrerUtilisateur(utilisateurDTO.getUid(),utilisateurDTO.getRoleUtilisateur());
    }

    @Test
    @DisplayName("creerUtilisateur: Levée d'une BadRequest exception si le body de la requête est incomplet")
    @WithMockUser(roles = "ADMIN")
    public void utilisateurDTO_Uncomplete_should_throw_an_Exception() throws Exception {
        //given
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setUid("a19390");

        monObjetMapper = objectMapper.writeValueAsString(utilisateurDTO);

        //when
            String resultat = mockMvc.perform(post("/gestaffectation/utilisateur/create")
                            .content(monObjetMapper)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse().getContentAsString();

        //then

        assertThat(resultat.contains("BAD REQUEST")).isTrue();

    }

    @Test
    @DisplayName("supprimerUtilisateur: Passage de paramètre entre l'URL et l'appel au service Utilisateur")
    @WithMockUser(roles = "ADMIN")
    public void supprimerUtilisateur_should_call_management_with_given_parameter() throws Exception {
        //given

        //when
        mockMvc.perform(get("/gestaffectation/utilisateur/delete/a19390")).andExpect(status().isOk());

        //then
        Mockito.verify(utilisateurManagement,Mockito.times(1)).supprimerUtilisateur("a19390");
    }

    @Test
    @DisplayName("ModifierMdp: Controle présence donnée obligatoire : uid + mdp")
    @WithMockUser(roles = "ADMIN")
    public void modifierMDP_should_fail_with_uid_or_mdp_empty() throws Exception {
        //given

        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setUid("");
        utilisateurDTO.setMdp("mdpDeTest");
        monObjetMapper = objectMapper.writeValueAsString(utilisateurDTO);
        //when
        String resultat = mockMvc.perform(post("/gestaffectation/utilisateur/update")
                .content(monObjetMapper).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
        //then
        assertThat(resultat).contains("BAD REQUEST");

    }

    @Test
    @DisplayName("modifierMDP : retour confirmation en mise à jour en cas de succès")
    @WithMockUser(roles = "ADMIN")
    public void modifierMDP_should_return_a_string_when_ok() throws Exception {
        //given

        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setUid("a19390");
        utilisateurDTO.setMdp("mdpDeTest");
        monObjetMapper = objectMapper.writeValueAsString(utilisateurDTO);

        Mockito.doNothing().when(utilisateurManagement).modifierMdpUtilisateur(utilisateurDTO.getUid(), utilisateurDTO.getMdp());


        //when
        String resultat = mockMvc.perform(post("/gestaffectation/utilisateur/update")
                .content(monObjetMapper).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();

        //then
        assertThat(resultat).isEqualTo("Le mot de passe de l'utilisateur est modifié");
    }

    @Test
    @DisplayName("ModifierMDP : Interdiction aux roles autre que ADMIN")
    @WithMockUser(roles = "TYPE1")
    public void modifierMdp_should_fait_with_roleType_other_than_admin() throws Exception {
        //given

        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setUid("a19390");
        utilisateurDTO.setMdp("mdpDeTest");
        monObjetMapper = objectMapper.writeValueAsString(utilisateurDTO);

        Mockito.doNothing().when(utilisateurManagement).modifierMdpUtilisateur(utilisateurDTO.getUid(), utilisateurDTO.getMdp());


        //when + then
          mockMvc.perform(post("/gestaffectation/utilisateur/update")
                .content(monObjetMapper).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());

    }

}