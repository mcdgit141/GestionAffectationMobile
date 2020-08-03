package com.epita.filrouge.infrastructure.affectation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRepositoryJpaAffectation extends JpaRepository<AffectationEntity , Long> {

      List<AffectationEntity> findByCollaborateurUid(String collaborateurUid);
}
