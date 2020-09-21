package com.epita.filrouge.expositions.affectation;

import com.epita.filrouge.AbstractMapper;
import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.expositions.collaborateur.CollaborateurFullDTO;
import com.epita.filrouge.expositions.collaborateur.CollaborateurFullDTOMapper;
import com.epita.filrouge.expositions.iphone.IphoneFullDTO;
import com.epita.filrouge.expositions.iphone.IphoneFullDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AffectationFullDTOMapper extends AbstractMapper<Affectation, AffectationFullDTO> {
    @Autowired
    CollaborateurFullDTOMapper collaborateurFullDTOMapper;

    @Autowired
    IphoneFullDTOMapper iphoneFullDTOMapper;

    @Override
    public Affectation mapToDomain(AffectationFullDTO affectationFullDTO) {
        return null;
    }

    @Override
    public AffectationFullDTO mapToDTO(Affectation affectation) {
        CollaborateurFullDTO collaborateurFullDTO = collaborateurFullDTOMapper.mapToDTO(affectation.getCollaborateur());
        IphoneFullDTO iphoneFullDTO = iphoneFullDTOMapper.mapToDTO(affectation.getIphone());

        AffectationFullDTO affectationFullDTO = new AffectationFullDTO();
        affectationFullDTO.setNumeroAffectation(affectation.getNumeroAffectation());
        affectationFullDTO.setDateAffectation(affectation.getDateAffectation());
        affectationFullDTO.setDateRenouvellementPrevue(affectation.getDateRenouvellementPrevue());
        affectationFullDTO.setDateFin(affectation.getDateFin());
        affectationFullDTO.setCommentaire(affectation.getCommentaire());
        affectationFullDTO.setMotifFin(affectation.getMotifFin());
        affectationFullDTO.setCollaborateur(collaborateurFullDTO);
        affectationFullDTO.setIphone(iphoneFullDTO);

        return affectationFullDTO;
    }
}
