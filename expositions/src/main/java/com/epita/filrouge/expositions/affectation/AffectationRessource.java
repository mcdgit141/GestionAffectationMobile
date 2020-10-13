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
import java.util.AbstractMap;
import java.util.List;


@RestController
@RequestMapping("/gestaffectation")

public class AffectationRessource {

    Logger monLogger = LoggerFactory.getLogger(AffectationRessource.class);

    @Autowired
    private IAffectationManagement affectationManagement;

    @Autowired
    private AffectationFullDTOMapper affectationFullDTOMapper;

//    @Autowired
//    private AffectationFullDTOMappertest affectationFullDTOMappertest;

    @PostMapping(value = "/affectation/creation", consumes = { "application/json" }, produces =  { "application/json" })
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_ADMIN","ROLE_TYPE2"})
    public AffectationFullDTO saveAffectation(@NotNull @Valid @RequestBody final AffectationFullDTO affectationFullDTO) {

        if (affectationFullDTO.getCollaborateur().getUid() != null & affectationFullDTO.getIphone().getNumeroSerie() != null
            & affectationFullDTO.getDateAffectation() != null & affectationFullDTO.getCollaborateur().getNumeroLigne() != null
            & affectationFullDTO.getCommentaire() != null & affectationFullDTO.getCollaborateur().getUid() != ""
            & affectationFullDTO.getIphone().getNumeroSerie() != "" & affectationFullDTO.getCollaborateur().getNumeroLigne() != ""
            & affectationFullDTO.getCommentaire() != "" )
        {

            Affectation affectationCree = affectationManagement.creerAffectation(affectationFullDTO.getCollaborateur().getUid(),
                    affectationFullDTO.getIphone().getNumeroSerie(),
                    affectationFullDTO.getDateAffectation(),
                    affectationFullDTO.getCollaborateur().getNumeroLigne(),
                    affectationFullDTO.getCommentaire());
            return affectationFullDTOMapper.mapToDTO(affectationCree);
        } else {
            throw new BadRequestException("Information(s) manquante(s) pour la création de l'affectation");
        }
    }

    @PostMapping(value = "/affectation/liste", consumes = { "application/json" }, produces =  { "application/json" })
    @Secured({"ROLE_ADMIN","ROLE_TYPE1","ROLE_TYPE2"})
    public List<AffectationFullDTO> afficheListeAffectations(@NotNull @RequestBody final FiltresAffectation filtresAffectation){

//        return affectationManagement.listerAffectation(filtresAffectation);
        List<Affectation> affectationList= affectationManagement.listerAffectation(filtresAffectation);
        return affectationFullDTOMapper.mapToDTOList(affectationList);
    }

    @PostMapping(value = "/affectation/liste2", consumes = { "application/json" }, produces =  { "application/json" })
    @Secured({"ROLE_ADMIN","ROLE_TYPE1","ROLE_TYPE2"})
    public MetaAffectationDTO afficheListeAffectations2(@NotNull @RequestBody final FiltresAffectation filtresAffectation){

//        return affectationManagement.listerAffectation(filtresAffectation);
        AbstractMap.Entry<List<Integer>, List<Affectation>> metaEtData= affectationManagement.listerAffectation2(filtresAffectation);

        List<Affectation> affectationList = metaEtData.getValue();
        List<Integer> metaData = metaEtData.getKey();

        MetaAffectationDTO.MetaDTO metaDTO = new MetaAffectationDTO.MetaDTO();
        metaDTO.setNombreTotalDEnregistrements(metaData.get(0));
        metaDTO.setNombreDePages(metaData.get(1));
        metaDTO.setNumeroDeLaPageRetournee(metaData.get(2));
        metaDTO.setNombreEnregistrementParPage(metaData.get(3));

        MetaAffectationDTO retour = new MetaAffectationDTO();
        retour.setMetadata(metaDTO);
        retour.setDatas(affectationFullDTOMapper.mapToDTOList(affectationList));
//        System.out.println("affectationFullDTOMapper.mapToDTOList(affectationList) = " + affectationFullDTOMapper.mapToDTOList(affectationList));
//        return affectationFullDTOMapper.mapToDTOList(affectationList);
        return retour;
    }



    @PutMapping(value = "/affectation/cloture", consumes = { "application/json" }, produces =  { "application/json" })
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN","ROLE_TYPE2"})
    public String clotureAffectation(@NotNull @RequestBody final AffectationFullDTO affectationFullDTO) {

        if (affectationFullDTO.getNumeroAffectation() != null & affectationFullDTO.getCommentaire() != null
           & affectationFullDTO.getMotifFin() != null & affectationFullDTO.getDateFin() != null
           & affectationFullDTO.getCommentaire() != "" & affectationFullDTO.getMotifFin() != ""

          ){
            affectationManagement.cloturerAffectation(Long.parseLong(affectationFullDTO.getNumeroAffectation()),affectationFullDTO.getCommentaire()
                    ,affectationFullDTO.getMotifFin(),affectationFullDTO.getDateFin());
            return "La clôture de l'affectation s'est bien passée";
        } else {

            throw new BadRequestException("Information(s) manquante(s) pour la clôture de l'affectation");
        }

    }

    @DeleteMapping(value = "/affectation/suppression")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Validated
    @Secured({"ROLE_ADMIN", "ROLE_TYPE2"})
    public void supprimerAffectation(@RequestParam final Long id,
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
