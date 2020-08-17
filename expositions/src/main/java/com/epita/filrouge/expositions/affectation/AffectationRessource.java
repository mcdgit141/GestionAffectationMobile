package com.epita.filrouge.expositions.affectation;

import com.epita.filrouge.application.affectation.IAffectationManagement;
import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.FiltresAffectation;
import com.epita.filrouge.domain.exception.BadRequestException;
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
    public Affectation saveAffectation(@NotNull @RequestBody final AffectationDTO affectationDTO) {
   //     throw new AllReadyExistException("foo");
        if (affectationDTO.getCollaborateurNumeroLigne() == null) {
            throw new BadRequestException("numéro de ligne non renseigné, donnée à saisir impérativement");
        }
        if (affectationDTO.getCollaborateurUid() == null) {
            throw new BadRequestException("collaborateur id non renseigné, donnée à saisir impérativement");
        }

        if (affectationDTO.getIphoneNumeroSerie() == null) {
            throw new BadRequestException("numéro série iphone non renseigné, donnée à saisir impérativement");
        }
        return  affectationManagement.creerAffectation(affectationDTO.getCollaborateurUid(),affectationDTO.getIphoneNumeroSerie(),
                                   affectationDTO.getAffectationDate(),affectationDTO.getCollaborateurNumeroLigne(),
                                   affectationDTO.getAffectationCommentaire());

    }

    @PostMapping(value = "/listeaffectation", consumes = { "application/json" }, produces =  { "application/json" })
    @Secured({"ROLE_ADMIN","ROLE_TYPE1","ROLE_TYPE2"})
    public List<Affectation> rechercheAffectation(@NotNull @RequestBody final FiltresAffectation filtresAffectation){
        final List<Affectation> affectations = affectationManagement.listerAffectation(filtresAffectation);
        return affectations;
    }
    @PostMapping(value = "/affectation/cloture", consumes = { "application/json" }, produces =  { "application/json" })
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_TYPE2")
    public String clotureAffectation(@NotNull @RequestBody final AffectationDTO affectationDTO) {

       if (affectationDTO.getNumeroAffectation() == null){
           throw new BadRequestException("numéro affectation non renseigné, donnée à saisir impérativement");
       }
        if (affectationDTO.getAffectationCommentaire() == "" && affectationDTO.getMotifFin() =="") {
            System.out.println("test couche exposition motif fin non renseigné");
            throw new BadRequestException("Motif de fin et Commentaire non renseignés, données à saisir impérativement");
        }
        if (affectationDTO.getMotifFin() == null && affectationDTO.getAffectationCommentaire() == null)
        {
            System.out.println("test couche exposition motif fin non renseigné");
            throw new BadRequestException("Motif de fin et Commentaire non renseignés, données à saisir impérativement");
        }
        if (affectationDTO.getAffectationCommentaire() == null || affectationDTO.getAffectationCommentaire() == "") {
            System.out.println("test couche exposition commentaire non renseigné");
            throw new BadRequestException("commentaire non renseigné, donnée à saisir impérativement");
        }

        if (affectationDTO.getMotifFin() == null || affectationDTO.getMotifFin() == "") {
            System.out.println("test couche exposition motif fin non renseigné");
            throw new BadRequestException("Motif de fin non renseigné, donnée à saisir impérativement");
        }

        affectationManagement.cloturerAffectation(affectationDTO.getNumeroAffectation(),affectationDTO.getAffectationCommentaire()
                ,affectationDTO.getMotifFin(),affectationDTO.getDateFin());
        return "La clôture de l'affectation s'est bien passée";
    }
}
