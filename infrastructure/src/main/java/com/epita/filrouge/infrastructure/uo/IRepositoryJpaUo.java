package com.epita.filrouge.infrastructure.uo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryJpaUo extends JpaRepository<UoEntity, Long> {

    UoEntity findByCodeUo (String codeUo);

    UoEntity findByNomUsageUo (String nomUsage);
}
