package com.epita.filrouge.expositions.collaborateur;

import com.epita.filrouge.application.collaborateur.ICollaborateurManagement;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.expositions.exception.FooMapperExceptionCode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({CollaborateurRessource.class, FooMapperExceptionCode.class})
class CollaborateurRessourceTest {
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

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICollaborateurManagement collaborateurManagement; //Mockito cree cette instance, rend un objet interface

    @Test
    @WithMockUser(roles = {"TYPE1","TYPE2","ADMIN"})
    void DoitRetournerInformationsCollaborateur_SurSaisieUid () throws Exception {
        // Given

        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);
        Uo uo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        uo.setSiteExercice(siteExercice);

        Collaborateur collaborateurRetour = new Collaborateur("425895", "Vivier", "D","0606060606",uo);
        when(collaborateurManagement.findByUid("425895")).thenReturn(collaborateurRetour);

      //  when(collaborateurManagement.findByUid(any(String.class))).thenReturn(collaborateurRetour);

        // When
        final String result = mockMvc.perform(get("/gestaffectation/collaborateur/{uid}", "425895")
                                                .accept(MediaType.APPLICATION_JSON))
                                                .andExpect(status().isOk())
                                    .andReturn().getResponse().getContentAsString();

        // Then
        assertThat(result).isEqualTo("{\"uid\":\"425895\",\"nom\":\"Vivier\",\"prenom\":\"D\",\"numeroLigne\":\"0606060606\"}");
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void role_user_ne_peux_pas_chercher_de_collaborateur () throws Exception {
        // Given
        SiteExercice siteExercice = new SiteExercice(CODE_SITE,NOM_SITE,ADRESSE_POSTALE,CODE_POSTAL,VILLE,PAYS,DATE_CREATION);
        Uo uo = new Uo(CODE_UO,FONCTION_RATTACHEMENT,CODE_UO_PARENT,NOM_USAGE_UO,NOM_RESPONSABLE_UO);
        uo.setSiteExercice(siteExercice);
        Collaborateur collaborateurRetour = new Collaborateur("425895", "Vivier", "D","0606060606",uo);
        when(collaborateurManagement.findByUid("425895")).thenReturn(collaborateurRetour);

        //  when(collaborateurManagement.findByUid(any(String.class))).thenReturn(collaborateurRetour);

        // When & then
       mockMvc.perform(get("/gestaffectation/collaborateur/{uid}", "425895")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

}