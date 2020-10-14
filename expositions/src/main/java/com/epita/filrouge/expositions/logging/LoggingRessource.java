package com.epita.filrouge.expositions.logging;

import com.epita.filrouge.expositions.utilisateur.UtilisateurRessource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/gestaffectation/logging")
//@ResponseStatus(HttpStatus.OK)
public class LoggingRessource {

    private Logger monLogger = LoggerFactory.getLogger(LoggingRessource.class);

    @PostMapping("/log")

    public void journaliserErreur(@RequestBody Map<String, Object> entry) {
        System.out.println(entry.get("erreur"));
        System.out.println(entry.get("url"));

        monLogger.info("Erreur Angular : "+entry.get("erreur") + " sur l'url : "+ entry.get("url"));

    }
}
