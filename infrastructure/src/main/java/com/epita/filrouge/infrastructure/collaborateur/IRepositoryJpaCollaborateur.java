package com.epita.filrouge.infrastructure.collaborateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface IRepositoryJpaCollaborateur extends JpaRepository<CollaborateurEntity, String> {

}


