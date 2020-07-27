package com.epita.filrouge.infrastructure.iphone;

import com.epita.filrouge.domain.iphone.ModeleIphone;
import com.epita.filrouge.infrastructure.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class ModeleIphoneEntityMapper extends AbstractMapper<ModeleIphone, ModeleIphoneEntity> {
    @Override
    public ModeleIphone mapToDomain(ModeleIphoneEntity modeleIphoneEntity) {
        return new ModeleIphone(modeleIphoneEntity.getModeleId(),modeleIphoneEntity.getNomModele());
    }

    @Override
    public ModeleIphoneEntity mapToEntity(final ModeleIphone modeleIphone) {
        final ModeleIphoneEntity modeleIphoneEntity = new ModeleIphoneEntity();
        modeleIphoneEntity.setModeleId(modeleIphone.getModeleID());
        modeleIphoneEntity.setNomModele(modeleIphone.getNomModele());
        return modeleIphoneEntity;
    }
}
