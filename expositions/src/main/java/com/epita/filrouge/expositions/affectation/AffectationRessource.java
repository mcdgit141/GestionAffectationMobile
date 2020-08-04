package com.epita.filrouge.expositions.affectation;

import com.epita.filrouge.application.affectation.IAffectationManagement;
import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.exception.AllReadyExistException;
import com.epita.filrouge.domain.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.nio.Buffer;
import java.util.List;

@RestController
@RequestMapping("/gestaffectation")

public class AffectationRessource {

    @Autowired
    private IAffectationManagement affectationManagement;

    @PostMapping(value = "/affectation", consumes = { "application/json" }, produces =  { "application/json" })
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_ADMIN","ROLE_TYPE2"})
    public void saveAffectation(@NotNull @RequestBody final AffectationDTO affectationDTO) {
   //     throw new AllReadyExistException("foo");
        System.out.println("DV  -  dans le post  mapping");
        affectationManagement.creerAffectation(affectationDTO.getCollaborateurUid(),affectationDTO.getIphoneNumeroSerie(),
                                   affectationDTO.getAffectationDate(),affectationDTO.getCollaborateurNumeroLigne(),
                                   affectationDTO.getAffectationCommentaire());

    }

    @GetMapping(value = "/listeaffectation", produces = { "application/json" })
    public List<Affectation> rechercheAffectation(){

        final List<Affectation> affectations = affectationManagement.listerAffectation();
        return affectations;
    }
}
