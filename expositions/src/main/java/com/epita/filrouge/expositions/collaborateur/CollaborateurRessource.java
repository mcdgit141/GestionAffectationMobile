package com.epita.filrouge.expositions.collaborateur;

import com.epita.filrouge.application.collaborateur.CollaborateurManagementImpl;
import com.epita.filrouge.application.collaborateur.ICollaborateurManagement;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/gestaffectation")
public class CollaborateurRessource {

    @Autowired
    ICollaborateurManagement collaborateurManagement;

    @GetMapping(value = "/test")
    public String test() {
        return "ok";
    }

    @GetMapping(value = "collaborateur/{uid}" , produces = {"application/json"})
    public CollaborateurFullDTO rechercheCollaborateurParUid (@PathVariable("uid") String uid) {

        System.out.println("on est rentre");
        Collaborateur collaborateur = collaborateurManagement.findByUid(uid);

        CollaborateurFullDTO collaborateurFullDTO = new CollaborateurFullDTO(collaborateur.getUid(), collaborateur.getNom(), collaborateur.getPrenom(), collaborateur.getNumeroLigne());

        return collaborateurFullDTO;
    }

}
