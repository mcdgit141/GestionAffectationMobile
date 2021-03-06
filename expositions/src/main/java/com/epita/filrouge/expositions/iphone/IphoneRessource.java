package com.epita.filrouge.expositions.iphone;

import com.epita.filrouge.application.iphone.IIphoneManagement;
import com.epita.filrouge.domain.iphone.Iphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gestaffectation")
public class IphoneRessource {

    @Autowired
    IIphoneManagement iphoneManagement;

    @Autowired
    IphoneDTOMapper iphoneDTOMapper;


    @GetMapping(value = "/iphone/{nommodele}" , produces = {"application/json"})
    public ResponseEntity<IphoneDTO> rechercheIphoneParModele (@PathVariable("nommodele") String nomModele) {


        final Iphone iphone = iphoneManagement.rechercheIphoneParNomModele(nomModele);

        final IphoneDTO iphoneDTO = iphoneDTOMapper.mapToDTO2(iphone);

        return new ResponseEntity<IphoneDTO>(iphoneDTO, HttpStatus.OK);

    }


}
