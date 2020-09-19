package com.epita.filrouge.expositions.collaborateur;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import org.springframework.stereotype.Component;

@Component
public class CollaborateurDTOMapper {

    public CollaborateurDTO mapToCollaborateurDTO (Collaborateur collaborateur) {

        CollaborateurDTO.UoDTO uoDTO = mapToUoDTO(collaborateur.getUo());

        return  new CollaborateurDTO(collaborateur.getUid(),
                                     collaborateur.getNom(),
                                     collaborateur.getPrenom(),
                                     collaborateur.getNumeroLigne(),
                                     uoDTO);
    }


    public CollaborateurDTO.UoDTO mapToUoDTO (Uo uo) {

        CollaborateurDTO.UoDTO uoDTO = new CollaborateurDTO.UoDTO();

        uoDTO.setCodeUo(uo.getCodeUo());
        uoDTO.setFonctionRattachement(uo.getFonctionRattachement());
        uoDTO.setCodeUoParent(uo.getCodeUoParent());
        uoDTO.setNomUsageUo(uo.getNomUsageUo());
        uoDTO.setNomResponsableUo(uo.getNomResponsableUo());

        uoDTO.setSiteExercice(mapToSiteExecercieDTO(uo.getSiteExercice()));

        return  uoDTO;
    }


    public CollaborateurDTO.SiteExerciceDTO mapToSiteExecercieDTO(SiteExercice siteExercice) {
        CollaborateurDTO.SiteExerciceDTO siteExerciceDTO =  new CollaborateurDTO.SiteExerciceDTO();

        siteExerciceDTO.setCodeSite(siteExercice.getCodeSite());
        siteExerciceDTO.setNomSite(siteExercice.getNomSite());
        siteExerciceDTO.setAdressePostale1(siteExercice.getAdressePostale1());
        siteExerciceDTO.setCodePostal(siteExercice.getCodePostal());
        siteExerciceDTO.setVille(siteExercice.getVille());
        siteExerciceDTO.setPays(siteExercice.getPays());
        siteExerciceDTO.setDateCreation(siteExercice.getDateCreation());
        siteExerciceDTO.setDateCloture(siteExercice.getDateCloture());

        return siteExerciceDTO;
    }
}
