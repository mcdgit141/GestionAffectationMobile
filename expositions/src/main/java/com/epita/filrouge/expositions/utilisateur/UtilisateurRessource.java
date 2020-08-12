package com.epita.filrouge.expositions.utilisateur;

import com.epita.filrouge.application.utilisateur.IUtilisateurManagement;
import com.epita.filrouge.domain.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/gestaffectation/utilisateur")
public class UtilisateurRessource {

    @Autowired
    private IUtilisateurManagement utilisateurManagement;

    private Logger monLogger = LoggerFactory.getLogger(UtilisateurRessource.class);

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_ADMIN")
    public void creerUtilisateur(@NotNull @RequestBody final UtilisateurDTO utilisateurDTO) {

        if (utilisateurDTO.getUid() != null & utilisateurDTO.getRoleUtilisateur() != null){
            utilisateurManagement.enregistrerUtilisateur(utilisateurDTO.getUid(),utilisateurDTO.getRoleUtilisateur());
        } else {
            throw new BadRequestException("Information(s) manquante(s) pour l'utilisateur à créer");
        }
    }

    @GetMapping(value = "/delete/{uid}")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public void supprimerUtilisateur(@NotNull @PathVariable("uid") String uid){
        utilisateurManagement.supprimerUtilisateur(uid);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        monLogger.warn("******** SUPPRESSION DE L'UTILISATEUR : " + uid + " *********");
        monLogger.warn("PAR : " + ((UserDetails) principal).getUsername());

    }

    @PostMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public String modifierMdp(@NotNull @RequestBody UtilisateurDTO utilisateurDTO){
        if ((utilisateurDTO.getUid() != null) &  (utilisateurDTO.getUid() != "") &
                (utilisateurDTO.getMdp() != null) & (utilisateurDTO.getMdp() != "")) {
            utilisateurManagement.modifierMdpUtilisateur(utilisateurDTO.getUid(), utilisateurDTO.getMdp());
            return "Le mot de passe de l'utilisateur est modifié";
        } else {
            throw new BadRequestException("Information(s) manquante(s) pour la mise à jour du Mdp");
        }
    }
}
