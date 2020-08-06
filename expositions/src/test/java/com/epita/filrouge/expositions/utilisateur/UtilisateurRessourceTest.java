package com.epita.filrouge.expositions.utilisateur;

import com.epita.filrouge.application.utilisateur.IUtilisateurManagement;
import com.epita.filrouge.domain.exception.BadRequestException;
import com.epita.filrouge.domain.exception.BusinessException;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;
import com.epita.filrouge.expositions.exception.MapperExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


import static org.assertj.core.api.Assertions.assertThat;
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
    @WithMockUser(roles = "ADMIN")
    public void role_admin_peut_creer_des_utilisateurs() throws Exception {

        // Given
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setUid("a19390");
        utilisateurDTO.setRoleUtilisateur(UtilisateurRoleEnum.ROLE_TYPE1);

        monObjetMapper = objectMapper.writeValueAsString(utilisateurDTO);

        //When
        mockMvc.perform(post("/gestaffectation/utilisateur/create")//
                .content(monObjetMapper) //
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isCreated());    // controle de l'AUTORISATION (spring security)

    }

    @Test
    @WithMockUser(roles = {"TYPE1","TYPE2"})
    public void role_autre_que_admin_ne_peut_pas_creer_des_utilisateurs() throws Exception {

        // Given
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setUid("a19390");
        utilisateurDTO.setRoleUtilisateur(UtilisateurRoleEnum.ROLE_TYPE1);

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
    @WithMockUser(roles = "ADMIN")
    public void creerUtilisateur_should_call_UtilisateurManegement_Once() throws Exception {
        //given
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setUid("a19390");
        utilisateurDTO.setRoleUtilisateur(UtilisateurRoleEnum.ROLE_TYPE1);

        monObjetMapper = objectMapper.writeValueAsString(utilisateurDTO);

        //when
        mockMvc.perform(post("/gestaffectation/utilisateur/create")
                    .content(monObjetMapper)
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        //then
        Mockito.verify(utilisateurManagement,Mockito.times(1)).enregistrerUtilisateur(utilisateurDTO.getUid(),utilisateurDTO.getRoleUtilisateur());
    }

    @Test
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
//                    .andExpect(model().attributeExists("BAD_REQUEST"));
                    .andReturn().getResponse().getContentAsString();

        //then

        assertThat(resultat.contains("BAD REQUEST")).isTrue();

    }
}