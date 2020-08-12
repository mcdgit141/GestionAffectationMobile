package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRepositoryJpaAffectation extends JpaRepository<AffectationEntity, Long> {

    List<AffectationEntity> findByCollaborateurUid(String collaborateurUid);

    List<AffectationEntity> findByIphoneNumeroSerie(String numeroSerie);

    AffectationEntity findByNumeroAffectation(Long numeroAffectation);
}
