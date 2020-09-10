package com.epita.filrouge.expositions.utilisateur;

import com.epita.filrouge.application.utilisateur.IUtilisateurManagement;
import com.epita.filrouge.domain.exception.BadRequestException;
import com.epita.filrouge.domain.utilisateur.Utilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/gestaffectation/utilisateur")
public class UtilisateurRessource {

    @Autowired
    private IUtilisateurManagement utilisateurManagement;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    @PostMapping(value = "/delete2")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public String supprimerUtilisateur2(@NotNull @RequestBody UtilisateurDTO utilisateurDTO){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails utilisateurEnSesion = (UserDetails) principal;

        if ((utilisateurDTO.getUsername().equals(utilisateurEnSesion.getUsername())) &
                (passwordEncoder.matches(utilisateurDTO.getPassword(),utilisateurEnSesion.getPassword()))) {

            utilisateurManagement.supprimerUtilisateur(utilisateurDTO.getUid());
            monLogger.warn("******** SUPPRESSION DE L'UTILISATEUR : " + utilisateurDTO.getUid() + " *********");
            monLogger.warn("PAR : " + utilisateurEnSesion.getUsername());
            return "L'utilisateur a été supprimé";
        } else {
            return "Les informations saisies ne correspondent pas à la session active";
        }
    }

    @DeleteMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public String supprimerUtilisateur(@NotNull @RequestParam("uid") String uid){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails utilisateurEnSesion = (UserDetails) principal;

        utilisateurManagement.supprimerUtilisateur(uid);
        monLogger.warn("******** SUPPRESSION DE L'UTILISATEUR : " + uid + " *********");
        monLogger.warn("PAR : " + utilisateurEnSesion.getUsername());
        return "L'utilisateur a été supprimé";

    }

    @GetMapping(value = "/retrieve/{uid}")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public Utilisateur rechercherUtilisateur(@NotNull @PathVariable("uid") String uid) {
        Utilisateur utilisateur = utilisateurManagement.rechercherUtilisateur(uid);
        return utilisateur;
    }

    @PostMapping(value = "/update2")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public String modifierMdp2(@NotNull @RequestBody UtilisateurDTO utilisateurDTO){
        if ((utilisateurDTO.getUid() != null) &  (utilisateurDTO.getUid() != "") &
                (utilisateurDTO.getMdp() != null) & (utilisateurDTO.getMdp() != "")) {
            utilisateurManagement.modifierMdpUtilisateur(utilisateurDTO.getUid(), utilisateurDTO.getMdp());
            return "Le mot de passe de l'utilisateur est modifié";
        } else {
            throw new BadRequestException("Information(s) manquante(s) pour la mise à jour du Mdp");
        }
    }

    @PutMapping(value = "/update")
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
