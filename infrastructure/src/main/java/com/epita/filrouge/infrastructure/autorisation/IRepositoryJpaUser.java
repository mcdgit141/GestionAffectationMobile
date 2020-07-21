package com.epita.filrouge.infrastructure.autorisation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryJpaUser extends JpaRepository<UserEntity, Long> {
    UserEntity findByLogin(String login);
}
