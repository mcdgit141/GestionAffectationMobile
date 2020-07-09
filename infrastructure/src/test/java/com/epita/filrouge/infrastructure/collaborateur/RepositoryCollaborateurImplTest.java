package com.epita.filrouge.infrastructure.collaborateur;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
//@DataJpaTest    // uniquement avec h2 ?
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryCollaborateurImplTest {

    @Autowired
    RepositoryCollaborateurImpl repositoryCollaborateurImpl;

    @Test
    void shouldReturnACollaborateur () {
        // Given

        // When
        Collaborateur collaborateurFounded = repositoryCollaborateurImpl.findByUid("425895");

        // Then
        assertThat(collaborateurFounded.getNom()).isEqualTo("Vivier");
        assertThat(collaborateurFounded.getPrenom()).isEqualTo("D");

    }
}