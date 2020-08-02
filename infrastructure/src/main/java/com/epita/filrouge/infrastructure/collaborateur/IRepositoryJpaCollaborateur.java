package com.epita.filrouge.infrastructure.collaborateur;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryJpaCollaborateur extends JpaRepository<CollaborateurEntity, Long> {

    CollaborateurEntity findByUid(String uid);

    CollaborateurEntity findByNumeroLigne(String numeroLigne);
}


