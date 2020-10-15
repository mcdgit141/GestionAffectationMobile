package com.epita.filrouge.application.iphone;

import com.epita.filrouge.application.iphone.IphoneManagementImpl;
import com.epita.filrouge.domain.iphone.EtatIphoneEnum;
import com.epita.filrouge.domain.iphone.IRepositoryIphone;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import com.epita.filrouge.domain.uo.IRepositoryUo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { IphoneManagementImpl.class } )
public class IphoneManagementImplTest {

    private static final Long MODELE_ID = 1L;
    private static final String MODELE_NOMMODELE = "Iphone8";

    private static final Long IPHONE_ID = 1L;
    private static final String IPHONE_NUMEROSERIE = "123456";
    private static final Double IPHONE_PRIX = 1200.00;
    private static final EtatIphoneEnum IPHONE_ETAT = EtatIphoneEnum.DISPONIBLE;

    @Autowired
    private IIphoneManagement iphoneManagementImpl;

    @MockBean
    private IRepositoryIphone repositoryIphone;

    @Test
    @DisplayName("Iphone: Recherche par nom modèle")
    void shouldCall_FindByNomModele(){

        // Given

        final ModeleIphone modeleIphone = new ModeleIphone(MODELE_ID, MODELE_NOMMODELE);
        final Iphone iphone = new Iphone(IPHONE_ID,IPHONE_NUMEROSERIE,IPHONE_PRIX,modeleIphone, IPHONE_ETAT);

        when(repositoryIphone.rechercheIphoneParNomModele(MODELE_NOMMODELE)).thenReturn(iphone);

        // When du test
        iphoneManagementImpl.rechercheIphoneParNomModele(MODELE_NOMMODELE);

        // Then
        verify(repositoryIphone, Mockito.times(1)).rechercheIphoneParNomModele(MODELE_NOMMODELE);
    }
}
