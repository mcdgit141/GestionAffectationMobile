package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryJpaIphone extends JpaRepository<IphoneEntity,Long> {

    IphoneEntity findByNomModele(String nomModele);
}
