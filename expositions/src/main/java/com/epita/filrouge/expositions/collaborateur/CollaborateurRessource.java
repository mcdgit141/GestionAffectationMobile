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

    @Autowired
    CollaborateurDTOMapper collaborateurDTOMapper;


    @Secured({"ROLE_TYPE1","ROLE_ADMIN"})
    @GetMapping(value = "/collaborateur/listeuid/{uid}" , produces = {"application/json"})
    public ResponseEntity<CollaborateurDTO> rechercheCollaborateurParUid (@PathVariable("uid") String uid) {

        final Collaborateur collaborateur = collaborateurManagement.findByUid(uid);

        final CollaborateurDTO collaborateurDTO = collaborateurDTOMapper.mapToCollaborateurDTO(collaborateur);


        return new ResponseEntity<>(collaborateurDTO, HttpStatus.OK);

    }


}
