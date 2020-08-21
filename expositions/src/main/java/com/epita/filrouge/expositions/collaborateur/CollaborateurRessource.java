package com.epita.filrouge.expositions.collaborateur;

import com.epita.filrouge.application.collaborateur.ICollaborateurManagement;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gestaffectation")
public class CollaborateurRessource {

    @Autowired
    ICollaborateurManagement collaborateurManagement;

    @GetMapping(value = "/test")
    public String test() {
        return "ok";
    }

    @Secured({"ROLE_TYPE1","ROLE_ADMIN"})
    @GetMapping(value = "/collaborateur/listeuid/{uid}" , produces = {"application/json"})
    public ResponseEntity<CollaborateurFullDTO> rechercheCollaborateurParUid (@PathVariable("uid") String uid) {

        final Collaborateur collaborateur = collaborateurManagement.findByUid(uid);

        final CollaborateurFullDTO collaborateurFullDTO = new CollaborateurFullDTO();
        collaborateurFullDTO.setNom(collaborateur.getNom());
        collaborateurFullDTO.setPrenom(collaborateur.getPrenom());
        collaborateurFullDTO.setUid(collaborateur.getUid());
        collaborateurFullDTO.setNumeroLigne(collaborateur.getNumeroLigne());

        return new ResponseEntity<CollaborateurFullDTO>(collaborateurFullDTO, HttpStatus.OK);

    }
    @Secured({"ROLE_TYPE1","ROLE_ADMIN"})
    @GetMapping(value = "/collaborateur/listenumeroligne/{numeroLigne}" , produces = {"application/json"})
    public ResponseEntity<CollaborateurFullDTO> rechercheCollaborateurParNumeroLigne (@PathVariable("numeroLigne") String numeroLigne) {

        final Collaborateur collaborateur = collaborateurManagement.findByNumeroLigne(numeroLigne);

        final CollaborateurFullDTO collaborateurFullDTO = new CollaborateurFullDTO();
        collaborateurFullDTO.setNom(collaborateur.getNom());
        collaborateurFullDTO.setPrenom(collaborateur.getPrenom());
        collaborateurFullDTO.setUid(collaborateur.getUid());
        collaborateurFullDTO.setNumeroLigne(collaborateur.getNumeroLigne());

        return new ResponseEntity<CollaborateurFullDTO>(collaborateurFullDTO, HttpStatus.OK);

    }

}
