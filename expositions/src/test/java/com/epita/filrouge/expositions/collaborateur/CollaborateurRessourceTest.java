package com.epita.filrouge.expositions.collaborateur;

import com.epita.filrouge.application.collaborateur.ICollaborateurManagement;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CollaborateurRessource.class)
class CollaborateurRessourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICollaborateurManagement collaborateurManagement; //Mockito cree cette instance, rend un objet interface

  //  private CollaborateurRessource collaborateurRessource; //ce que l'on veut tester

    @Test
    void DoitRetournerInformationsCollaborateur_SurSaisieUid () throws Exception {
        // Given
       // collaborateurRessource = new CollaborateurRessource();
     //   collaborateurRessource.setCollaborateurManagement(collaborateurManagement);

        Collaborateur collaborateurRetour = new Collaborateur("425895", "Vivier", "D");
        when(collaborateurManagement.findByUid("425895")).thenReturn(collaborateurRetour);

      //  when(collaborateurManagement.findByUid(any(String.class))).thenReturn(collaborateurRetour);

        // When
        final String result = mockMvc.perform(get("/gestaffectation/collaborateur/{uid}", "425895")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        // Then
        assertThat(result).isEqualTo("{\"uid\":\"425895\",\"nom\":\"Vivier\",\"prenom\":\"D\"}");
    }

}