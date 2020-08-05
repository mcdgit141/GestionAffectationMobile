package com.epita.filrouge.domain.iphone;

import com.epita.filrouge.domain.exception.NotFoundException;

public interface IRepositoryIphone {

    Iphone findByNomModele (String nomModele);

    Iphone findByNumeroSerie(String iPhoneNumeroSerie);

    void miseAJourEtatIphone(String iphoneNumeroSerie,  EtatIphoneEnum etatIphoneEnum);
}
