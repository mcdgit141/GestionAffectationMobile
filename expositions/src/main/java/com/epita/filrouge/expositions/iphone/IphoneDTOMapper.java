package com.epita.filrouge.expositions.iphone;

import com.epita.filrouge.AbstractMapper;
import com.epita.filrouge.domain.iphone.Iphone;
import com.epita.filrouge.domain.iphone.ModeleIphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IphoneDTOMapper {

    //    @Override
    public Iphone mapToDomain(IphoneFullDTO iphoneDTO) {
        return null;
    }

    //    @Override
    public IphoneDTO mapToDTO(Iphone iphone) {
        return new IphoneDTO(iphone.getIphoneId(), iphone.getNumeroSerie());
    }

    public IphoneDTO mapToDTO2(Iphone iphone) {
        IphoneDTO.ModeleIphoneDTO modeleIphoneDTO = mapToModeleIphoneDTO(iphone.getModeleIphone());
        return new IphoneDTO(iphone.getNumeroSerie(), iphone.getPrixIphone(), modeleIphoneDTO,iphone.getEtatIphone());
    }

    public IphoneDTO.ModeleIphoneDTO mapToModeleIphoneDTO(ModeleIphone modeleIphone) {
        return new IphoneDTO.ModeleIphoneDTO(modeleIphone.getModeleID(), modeleIphone.getNomModele());
    }
}
