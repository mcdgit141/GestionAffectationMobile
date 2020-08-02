package com.epita.filrouge.infrastructure.iphone;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryJpaModeleIphone extends JpaRepository <ModeleIphoneEntity,Long>{

    ModeleIphoneEntity findByNomModele(String nomModele);
}
