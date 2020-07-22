package com.epita.filrouge.domain.iphone;

public interface IRepositoryIphone {

    public Iphone findByNomModele (String nomModele);

    public Iphone findByNumeroSerie(String iPhoneNumeroSerie);

    void miseAJourEtatIphone(Iphone iPhone, String iphoneNumeroSerie);
}
