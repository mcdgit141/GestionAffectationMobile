package com.epita.filrouge.expositions.utilisateur;

import com.epita.filrouge.domain.utilisateur.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurDtoMapper {


    public UtilisateurDTO mapToDto(Utilisateur utilisateur) {
        UtilisateurDTO utilisateurARetourner = new UtilisateurDTO();
        utilisateurARetourner.setUid(utilisateur.getUid());
        utilisateurARetourner.setNom(utilisateur.getNom());
        utilisateurARetourner.setPrenom(utilisateur.getPrenom());
        utilisateurARetourner.setLogin(utilisateur.getLogin());
        utilisateurARetourner.setRoleUtilisateur(utilisateur.getUserRole().toString());
        utilisateurARetourner.setPassword("********");
        return utilisateurARetourner;
    }
}
