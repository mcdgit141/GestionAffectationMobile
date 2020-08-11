package com.epita.filrouge.domain.Utilisateur;

import com.epita.filrouge.domain.utilisateur.Utilisateur;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class UtilisateurTest {

    @Test
    @DisplayName("création d'un Utilisateur avec un mdp par defaut")
    public void utilisateur_should_be_created_with_default_password(){
        //giving
        String uid = "a19390";
        String nom = "DUPOND";
        String prenom = "François";
        UtilisateurRoleEnum role = UtilisateurRoleEnum.ROLE_TYPE1;

        //when
        Utilisateur utilisateurCree = new Utilisateur(uid,nom,prenom,role);

        //then
        assertThat(utilisateurCree.getPassword()).isNotNull();
    }


    @Test
    @DisplayName("Construction d'un login à la création d'un Utilisateur")
    public void utilisateur_should_be_constructed_with_a_login(){
        //giving
        String uid = "a19390";
        String nom = "DUPOND";
        String prenom = "François";
        UtilisateurRoleEnum role = UtilisateurRoleEnum.ROLE_TYPE1;

        //when
        Utilisateur utilisateurCree = new Utilisateur(uid,nom,prenom,role);

        //then
        assertThat(utilisateurCree.getLogin()).isEqualTo("francois.dupond@entreprise.com");

    }

//    @Test
//    @DisplayName("TU de la methode de remplacement des caractère spéciaux dans une String")
//    public void remplacementCaractereSpeciaux_should_return_a_string_without_specialChar(){
//        //given
//
//        //when
//        String resultat = Utilisateur.remplacementCaractereSpec("Léöpôld");
//
//        //then
//        assertThat(resultat).isEqualTo("leopold");
//    }
//    @Test
//    @DisplayName("contruction d'un login à partir du nom et prénom de l'Utilisateur")
//    public void construireLogin_should_add_a_login_to_Utilisateur(){
//        //giving
//        String uid = "a19390";
//        String nom = "DUPOND";
//        String prenom = "Francois";
//        UtilisateurRoleEnum role = UtilisateurRoleEnum.ROLE_TYPE1;
//        Utilisateur utilisateurCree = new Utilisateur(uid,nom,prenom,role);
//
//        //when
//            utilisateurCree.construireLogin();
//
//        //then
//        assertThat(utilisateurCree.getLogin()).isEqualTo("francois.dupond@entreprise.com");
//    }
}
