package com.epita.filrouge.expositions.affectation;

import com.epita.filrouge.AbstractMapper;
import com.epita.filrouge.domain.affectation.Affectation;
import com.epita.filrouge.expositions.collaborateur.CollaborateurFullDTO;
import com.epita.filrouge.expositions.collaborateur.CollaborateurFullDTOMapper;
import com.epita.filrouge.expositions.iphone.IphoneFullDTO;
import com.epita.filrouge.expositions.iphone.IphoneFullDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Component;

@Component
public class AffectationDTOMapper extends AbstractMapper<Affectation, AffectationDTO> {

    @Autowired
    CollaborateurFullDTOMapper collaborateurFullDTOMapper;

    @Autowired
    IphoneFullDTOMapper iphoneFullDTOMapper;

    @Override
    public Affectation mapToDomain(AffectationDTO affectationDTO) {
        return null;
    }

    @Override
    public AffectationDTO mapToDTO(Affectation affectation) {
        return null;
    }
}
