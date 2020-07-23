package com.epita.filrouge.infrastructure.autorisation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryJpaUtilisateur extends JpaRepository<UtilisateurEntity, Long> {
    UtilisateurEntity findByLogin(String login);
}
