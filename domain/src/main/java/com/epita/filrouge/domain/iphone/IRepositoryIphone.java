package com.epita.filrouge.domain.iphone;

import com.epita.filrouge.domain.exception.NotFoundException;

public interface IRepositoryIphone {

    public Iphone findByNomModele (String nomModele) throws NotFoundException;

    public Iphone findByNumeroSerie(String iPhoneNumeroSerie) throws NotFoundException;

    public void miseAJourEtatIphone(Iphone iPhone, String iphoneNumeroSerie);
}
