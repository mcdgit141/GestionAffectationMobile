package com.epita.filrouge.domain.iphone;

import com.epita.filrouge.domain.exception.NotFoundException;

public interface IRepositoryModeleIphone {

    public ModeleIphone findNomModele(String nomModele) throws NotFoundException;
}
