package com.epita.filrouge.infrastructure.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IRepositoryJpaAffectation extends JpaRepository<AffectationEntity , Long> {

   List<AffectationEntity> findByCollabarateurEntityUid(String collaborateurUid);

}
