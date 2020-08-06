package com.epita.filrouge.expositions.utilisateur;

import com.epita.filrouge.application.utilisateur.IUtilisateurManagement;
import com.epita.filrouge.domain.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/gestaffectation/utilisateur")
public class UtilisateurRessource {

    @Autowired
    private IUtilisateurManagement utilisateurManagement;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_ADMIN")
    public void creerUtilisateur(@NotNull @RequestBody final UtilisateurDTO utilisateurDTO) {

        if (utilisateurDTO.getUid() != null & utilisateurDTO.getRoleUtilisateur() != null){
            utilisateurManagement.enregistrerUtilisateur(utilisateurDTO.getUid(),utilisateurDTO.getRoleUtilisateur());
        } else {
            throw new BadRequestException("Informations manquantes pour l'utilisateur a creer");
        }
    }
}
