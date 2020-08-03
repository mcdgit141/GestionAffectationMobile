package com.epita.filrouge.domain.iphone;

import com.epita.filrouge.domain.exception.NotFoundException;

public interface IRepositoryIphone {

    Iphone findByNomModele (String nomModele) throws NotFoundException;

    Iphone findByNumeroSerie(String iPhoneNumeroSerie) throws NotFoundException;

    void miseAJourEtatIphone(String iphoneNumeroSerie);
}
