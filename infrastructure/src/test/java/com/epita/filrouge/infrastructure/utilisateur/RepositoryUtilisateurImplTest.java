package com.epita.filrouge.infrastructure.utilisateur;

import com.epita.filrouge.domain.utilisateur.Utilisateur;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;
import com.epita.filrouge.infrastructure.affectation.RepositoryAffectationImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepositoryUtilisateurImplTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    RepositoryUtilisateurImpl repositoryUtilisateur;

    private Utilisateur monUtitlisateur;

    @Test
    @DisplayName("Creation d'un UtilisateurEntity A partir d'un Utilisateur")
    public void creerUser_should_save_an_UtilisateurEntity(){
        //giving
        monUtitlisateur = new Utilisateur("a19390","DUPOND","Francois", UtilisateurRoleEnum.ROLE_TYPE1);
        monUtitlisateur.setLogin("login_de_test@entreprise.com");
        monUtitlisateur.setPassword("password");

        //when
        repositoryUtilisateur.creerUser(monUtitlisateur);

        //then
        UtilisateurEntity utilisateurEntityCree = (UtilisateurEntity) entityManager.getEntityManager()
                                    .createQuery("select u from UtilisateurEntity u where uid = 'a19390' ")
                                    .getSingleResult();
        assertThat(utilisateurEntityCree).isNotNull();
        assertThat(utilisateurEntityCree.getPassword()).isEqualTo(monUtitlisateur.getPassword());
        assertThat(utilisateurEntityCree.getLogin()).isEqualTo(monUtitlisateur.getLogin());
        assertThat(utilisateurEntityCree.getUserRole()).isEqualTo(monUtitlisateur.getUserRole());

    }
}
