package com.epita.filrouge.expositions.affectation;

import com.epita.filrouge.application.affectation.IAffectationManagement;
import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.domain.affectation.FiltresAffectation;
import com.epita.filrouge.domain.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/gestaffectation")

public class AffectationRessource {

    Logger monLogger = LoggerFactory.getLogger(AffectationRessource.class);

    @Autowired
    private IAffectationManagement affectationManagement;

    @Autowired
    private AffectationFullDTOMapper affectationFullDTOMapper;

    @PostMapping(value = "/affectation/creation", consumes = { "application/json" }, produces =  { "application/json" })
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_ADMIN","ROLE_TYPE2"})
    public AffectationFullDTO saveAffectation(@NotNull @Valid @RequestBody final AffectationDTO affectationDTO) {

        if (affectationDTO.getAffectationCommentaire() == "" && affectationDTO.getCollaborateurNumeroLigne() == "" ||
                affectationDTO.getAffectationCommentaire() == null && affectationDTO.getCollaborateurNumeroLigne() == null) {
            System.out.println("test couche exposition motif fin non renseigné");
            throw new BadRequestException("Commentaire et numéro de ligne non renseignés, données à saisir impérativement");
        }
        if (affectationDTO.getCollaborateurNumeroLigne() == null || affectationDTO.getCollaborateurNumeroLigne() == "" ) {
            throw new BadRequestException("numéro de ligne non renseigné, donnée à saisir impérativement");
        }
        if (affectationDTO.getCollaborateurNumeroLigne().length() > 10 || affectationDTO.getCollaborateurNumeroLigne().length() < 10){
            throw new BadRequestException("numéro de ligne ne comporte pas les 10 chiffres attendus");
        }
        if (affectationDTO.getCollaborateurUid() == null || affectationDTO.getCollaborateurUid() == "" ) {
            throw new BadRequestException("collaborateur id non renseigné, donnée à saisir impérativement");
        }

        if (affectationDTO.getIphoneNumeroSerie() == null || affectationDTO.getIphoneNumeroSerie() == "") {
            throw new BadRequestException("numéro série iphone non renseigné, donnée à saisir impérativement");
        }
        if (affectationDTO.getAffectationCommentaire() == null || affectationDTO.getAffectationCommentaire() == "" ) {
            throw new BadRequestException("Commentaire non renseigné, donnée à saisir impérativement");
        }
        if (affectationDTO.getAffectationDate().isBefore(LocalDate.now()) || affectationDTO.getAffectationDate().isAfter(LocalDate.now())){
            throw new BadRequestException("La date d'affectation doit être à la date du jour");
        }

//        return affectationManagement.creerAffectation(affectationDTO.getCollaborateurUid(),affectationDTO.getIphoneNumeroSerie(),
//                affectationDTO.getAffectationDate(),affectationDTO.getCollaborateurNumeroLigne(),
//                affectationDTO.getAffectationCommentaire());

        Affectation affectationCree = affectationManagement.creerAffectation(affectationDTO.getCollaborateurUid(),affectationDTO.getIphoneNumeroSerie(),
                affectationDTO.getAffectationDate(),affectationDTO.getCollaborateurNumeroLigne(),
                affectationDTO.getAffectationCommentaire());
        return affectationFullDTOMapper.mapToDTO(affectationCree);
    }

    @PostMapping(value = "/affectation/liste", consumes = { "application/json" }, produces =  { "application/json" })
    @Secured({"ROLE_ADMIN","ROLE_TYPE1","ROLE_TYPE2"})
    public List<AffectationFullDTO> afficheListeAffectations(@NotNull @RequestBody final FiltresAffectation filtresAffectation){

//        return affectationManagement.listerAffectation(filtresAffectation);
        List<Affectation> affectationList= affectationManagement.listerAffectation(filtresAffectation);
        return affectationFullDTOMapper.mapToDTOList(affectationList);
    }

    @PutMapping(value = "/affectation/cloture", consumes = { "application/json" }, produces =  { "application/json" })
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN","ROLE_TYPE2"})
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


    @DeleteMapping(value = "/affectation/suppression", consumes = { "application/json" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN", "ROLE_TYPE2"})
    public void supprimerAffectation(@NotNull @Valid  @RequestBody final SuppressionDTO affectationASupprimer){
        monLogger.debug("");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        monLogger.info("Suppression d'affectation demandée par : {}", name);
        affectationManagement.supprimerAffectation(affectationASupprimer.numeroAffectation);
    }


    @DeleteMapping(value = "/affectation/suppression2", consumes = { "application/json" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Validated
    @Secured({"ROLE_ADMIN", "ROLE_TYPE2"})
    public void supprimerAffectation2(@RequestParam final Long id,
                                      @RequestParam final String commentaire){
        monLogger.debug("");
        if (id == null){
            throw new BadRequestException("numéro affectation non renseigné, donnée à saisir impérativement");
        }
        if (commentaire.length() < 5) {
            throw new BadRequestException("le commentaire doit etre d'au moins 5 caracteres");

        }
        Affectation affectationSupprimee = affectationManagement.supprimerAffectation(id);
        String name2 = SecurityContextHolder.getContext().getAuthentication().getName();
        monLogger.warn("");
        monLogger.warn("Suppression d'affectation demandée par  \"{}\", commentaire : {}", name2, commentaire);
        monLogger.warn(" Affectation : {}", affectationSupprimee);

    }

}
