package com.epita.filrouge.domain.iphone;

import com.epita.filrouge.domain.exception.NotFoundException;

public interface IRepositoryIphone {

    Iphone rechercheIphoneParNomModele (String nomModele) throws NotFoundException;

    Iphone rechercheIphoneParNumeroSerie(String iPhoneNumeroSerie) throws NotFoundException;

}
