package com.epita.filrouge.infrastructure.utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositoryJpaUtilisateur extends JpaRepository<UtilisateurEntity, Long> {
    Optional<UtilisateurEntity> findByLogin(String login);

    Optional<UtilisateurEntity> findByCollaborateurLightUid(String uid);
}

