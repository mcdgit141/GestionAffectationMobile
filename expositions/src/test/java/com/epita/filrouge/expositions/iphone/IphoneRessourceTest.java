package com.epita.filrouge.expositions.iphone;

import com.epita.filrouge.application.iphone.IIphoneManagement;
import com.epita.filrouge.application.security.UserDetailServiceImpl;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import com.epita.filrouge.domain.utilisateur.IRepositoryUtilisateur;
import com.epita.filrouge.expositions.exception.MapperExceptionCode;
import com.epita.filrouge.jwt.JwtAuthenticationEntryPoint;
import com.epita.filrouge.jwt.JwtRequestFilter;
import com.epita.filrouge.jwt.JwtUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({IphoneRessource.class, MapperExceptionCode.class, IphoneDTOMapper.class, JwtUtils.class, JwtRequestFilter.class, JwtAuthenticationEntryPoint.class})
//@Disabled
public class IphoneRessourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IIphoneManagement iPhoneManagement; //Mockito cree cette instance, rend un objet interface

    @MockBean
    private IRepositoryUtilisateur repositoryUtilisateur;

    @MockBean
    private UserDetailServiceImpl userDetailService;


    @Test
    @WithMockUser(roles = {"TYPE1","TYPE2","ADMIN"})
    @DisplayName("Pour un modele d'iphone, doit retourner une iphone DTO")
    void AvecNoueauDTO_DoitRetournerIphone_SurSaisieNomModele () throws Exception {
        // Given

        final  String nomModele = "Iphone8";
        final String numeroSerie = "010203A";
        final double prix = 1400.50d;
        final String etat = EtatIphoneEnum.DISPONIBLE.name();

        ModeleIphone modeleIphone = new ModeleIphone(1L,nomModele);
        Iphone iphoneRetour = new Iphone(1L,numeroSerie,prix,modeleIphone, EtatIphoneEnum.DISPONIBLE);

        when(iPhoneManagement.rechercheIphoneParNomModele(nomModele)).thenReturn(iphoneRetour);

        // When
        mockMvc.perform(get("/gestaffectation/iphone/{nommodele}", nomModele)
                .accept(MediaType.APPLICATION_JSON))
        // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroSerie").value(numeroSerie))
                .andExpect(jsonPath("$.etatIphone").value(EtatIphoneEnum.DISPONIBLE.name()))
                .andExpect(jsonPath("$.modeleIphoneDTO.nomModele").value(nomModele));
    }


}
