package com.epita.filrouge.expositions.autorisation;

import com.epita.filrouge.application.autorisation.IUtilisateurManagement;
import com.epita.filrouge.domain.autorisation.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/gestaffectation/utilisateur")
public class UtilisateurRessource {


    IUtilisateurManagement utilisateurManagement;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void creerUtilisateur(@NotNull @RequestBody final UtilisateurDTO utilisateurDTO) {
        utilisateurManagement.enregistrerUtilisateur(utilisateurDTO.getUid(), utilisateurDTO.getLogin());
    }
}
