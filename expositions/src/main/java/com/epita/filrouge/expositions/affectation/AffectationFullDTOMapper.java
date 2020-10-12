package com.epita.filrouge.expositions.affectation;

import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.expositions.collaborateur.CollaborateurDTO;
import com.epita.filrouge.expositions.collaborateur.CollaborateurDTOMapper;
import com.epita.filrouge.expositions.iphone.IphoneDTO;
import com.epita.filrouge.expositions.iphone.IphoneDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AffectationFullDTOMapper  {
    @Autowired
    CollaborateurDTOMapper collaborateurDTOMapper;

    @Autowired
    IphoneDTOMapper iphoneDTOMapper;

    public AffectationFullDTO mapToDTO(Affectation affectation) {
        CollaborateurDTO collaborateurFullDTO = collaborateurDTOMapper.mapToCollaborateurDTO(affectation.getCollaborateur());
        IphoneDTO iphoneFullDTO = iphoneDTOMapper.mapToDTO2(affectation.getIphone());

        AffectationFullDTO affectationFullDTO = new AffectationFullDTO();
        affectationFullDTO.setNumeroAffectation(affectation.getNumeroAffectation().toString());
        affectationFullDTO.setDateAffectation(affectation.getDateAffectation());
        affectationFullDTO.setDateRenouvellementPrevue(affectation.getDateRenouvellementPrevue());
        affectationFullDTO.setDateFin(affectation.getDateFin());
        affectationFullDTO.setCommentaire(affectation.getCommentaire());
        affectationFullDTO.setMotifFin(affectation.getMotifFin());
        affectationFullDTO.setCollaborateur(collaborateurFullDTO);
        affectationFullDTO.setIphone(iphoneFullDTO);

        return affectationFullDTO;
    }

    public List<AffectationFullDTO> mapToDTOList(List<Affectation> affectationList) {

        return affectationList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
