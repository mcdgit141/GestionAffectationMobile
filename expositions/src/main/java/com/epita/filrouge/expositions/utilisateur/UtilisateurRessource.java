package com.epita.filrouge.expositions.utilisateur;

import com.epita.filrouge.application.utilisateur.IUtilisateurManagement;
import com.epita.filrouge.domain.exception.BadRequestException;
import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        UtilisateurRoleEnum roleUtilisateurACreer;

        if (utilisateurDTO.getUid() != null & utilisateurDTO.getRoleUtilisateur() != null){
            utilisateurManagement.enregistrerUtilisateur(utilisateurDTO.getUid(),utilisateurDTO.getRoleUtilisateur());
        } else {
            throw new BadRequestException("Information(s) manquante(s) pour l'utilisateur à créer");
        }
    }

    @GetMapping(value = "/delete/{uid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured("ROLE_ADMIN")
    public void supprimerUtilisateur(@NotNull @PathVariable("uid") String uid){
        utilisateurManagement.supprimerUtilisateur(uid);
    }

}
