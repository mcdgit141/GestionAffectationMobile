package com.epita.filrouge.infrastructure.collaborateur;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.infrastructure.AbstractMapper;
import com.epita.filrouge.infrastructure.affectation.AffectationEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollaborateurEntityMapper extends AbstractMapper<Collaborateur,CollaborateurEntity> {
    @Autowired
    private AffectationEntityMapper affectationMapper;

    @Override
    public Collaborateur mapToDomain(final CollaborateurEntity entity) {


        return null;
    }

    @Override
    public CollaborateurEntity mapToEntity(Collaborateur dto) {
        return null;
    }
}
