package com.epita.filrouge.expositions.collaborateur;

import com.epita.filrouge.AbstractMapper;
import com.epita.filrouge.domain.collaborateur.Collaborateur;
import org.springframework.stereotype.Component;

@Component
public class CollaborateurFullDTOMapper extends AbstractMapper<Collaborateur, CollaborateurFullDTO> {
    @Override
    public Collaborateur mapToDomain(CollaborateurFullDTO collaborateurFullDTO) {
        return null;
    }

    @Override
    public CollaborateurFullDTO mapToDTO(Collaborateur collaborateur) {
        CollaborateurFullDTO collaborateurFullDTO =  new CollaborateurFullDTO();
        collaborateurFullDTO.setUid(collaborateur.getUid());
        collaborateurFullDTO.setNom(collaborateur.getNom());
        collaborateurFullDTO.setPrenom(collaborateur.getPrenom());
        collaborateurFullDTO.setNumeroLigne(collaborateur.getNumeroLigne());

        return collaborateurFullDTO;


    }
}
