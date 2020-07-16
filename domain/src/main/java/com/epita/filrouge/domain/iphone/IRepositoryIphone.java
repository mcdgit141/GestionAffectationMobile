package com.epita.filrouge.domain.iphone;

public interface IRepositoryIphone {

    public Iphone findByNomModele (String nomModele);

    public Iphone findBynumeroSerie(String iPhoneNumeroSerie);
}
