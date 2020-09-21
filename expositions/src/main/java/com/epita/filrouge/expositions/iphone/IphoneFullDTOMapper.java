package com.epita.filrouge.expositions.iphone;

import com.epita.filrouge.AbstractMapper;
import com.epita.filrouge.domain.iphone.Iphone;
import org.springframework.stereotype.Component;

@Component
public class IphoneFullDTOMapper extends AbstractMapper<Iphone,IphoneFullDTO> {
    @Override
    public Iphone mapToDomain(IphoneFullDTO iphoneFullDTO) {
        return null;
    }

    @Override
    public IphoneFullDTO mapToDTO(Iphone iphone) {
        return new IphoneFullDTO(iphone.getIphoneId(), iphone.getNumeroSerie());
    }
}
