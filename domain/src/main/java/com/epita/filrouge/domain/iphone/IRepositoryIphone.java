package com.epita.filrouge.domain.iphone;

public interface IRepositoryIphone {

    Iphone rechercheIphoneParNomModele (String nomModele);

    Iphone rechercheIphoneParNumeroSerie(String iPhoneNumeroSerie);

    void miseAJourEtatIphone(String iphoneNumeroSerie,  EtatIphoneEnum etatIphoneEnum);
}
