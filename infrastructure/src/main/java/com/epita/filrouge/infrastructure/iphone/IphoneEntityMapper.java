package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.infrastructure.AbstractMapper;
import com.epita.filrouge.infrastructure.affectation.AffectationEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IphoneEntityMapper extends AbstractMapper<Iphone, IphoneEntity> {

    @Autowired
    private ModeleIphoneEntityMapper modeleIphoneMapper;

    @Autowired
    private AffectationEntityMapper affectationMapper;


    @Override
    public Iphone mapToDomain(IphoneEntity entity) {
        return null;
    }

    @Override
    public IphoneEntity mapToEntity(Iphone dto) {
        return null;
    }
}
